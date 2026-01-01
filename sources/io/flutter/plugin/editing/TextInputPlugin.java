package io.flutter.plugin.editing;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.Editable;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import androidx.core.view.inputmethod.EditorInfoCompat;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import io.flutter.Log;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.engine.systemchannels.ScribeChannel;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.editing.ListenableEditingState;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.plugin.platform.PlatformViewsController2;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class TextInputPlugin implements ListenableEditingState.EditingStateWatcher {
    private static final String TAG = "TextInputPlugin";
    private final AutofillManager afm;
    private SparseArray<TextInputChannel.Configuration> autofillConfiguration;
    private TextInputChannel.Configuration configuration;
    private ImeSyncDeferringInsetsCallback imeSyncCallback;
    private InputTarget inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
    private boolean isInputConnectionLocked;
    private Rect lastClientRect;
    private InputConnection lastInputConnection;
    private ListenableEditingState mEditable;
    private final InputMethodManager mImm;
    private TextInputChannel.TextEditState mLastKnownFrameworkTextEditingState;
    private boolean mRestartInputPending;
    private final View mView;
    private PlatformViewsController platformViewsController;
    private PlatformViewsController2 platformViewsController2;
    private final ScribeChannel scribeChannel;
    private final TextInputChannel textInputChannel;

    private interface MinMax {
        void inspect(double d, double d2);
    }

    public TextInputPlugin(View view, TextInputChannel textInputChannel, ScribeChannel scribeChannel, PlatformViewsController platformViewsController, PlatformViewsController2 platformViewsController2) {
        this.mView = view;
        this.mEditable = new ListenableEditingState(null, view);
        this.mImm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (Build.VERSION.SDK_INT >= 26) {
            this.afm = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m458m(view.getContext().getSystemService(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m460m()));
        } else {
            this.afm = null;
        }
        if (Build.VERSION.SDK_INT >= 30) {
            ImeSyncDeferringInsetsCallback imeSyncDeferringInsetsCallback = new ImeSyncDeferringInsetsCallback(view);
            this.imeSyncCallback = imeSyncDeferringInsetsCallback;
            imeSyncDeferringInsetsCallback.install();
        }
        this.textInputChannel = textInputChannel;
        textInputChannel.setTextInputMethodHandler(new TextInputChannel.TextInputMethodHandler() { // from class: io.flutter.plugin.editing.TextInputPlugin.1
            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void show() {
                TextInputPlugin textInputPlugin = TextInputPlugin.this;
                textInputPlugin.showTextInput(textInputPlugin.mView);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void hide() {
                if (TextInputPlugin.this.inputTarget.type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
                    TextInputPlugin.this.notifyViewExited();
                } else {
                    TextInputPlugin textInputPlugin = TextInputPlugin.this;
                    textInputPlugin.hideTextInput(textInputPlugin.mView);
                }
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void requestAutofill() {
                TextInputPlugin.this.notifyViewEntered();
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void finishAutofillContext(boolean z) {
                if (Build.VERSION.SDK_INT < 26 || TextInputPlugin.this.afm == null) {
                    return;
                }
                if (z) {
                    TextInputPlugin.this.afm.commit();
                } else {
                    TextInputPlugin.this.afm.cancel();
                }
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setClient(int i, TextInputChannel.Configuration configuration) {
                TextInputPlugin.this.setTextInputClient(i, configuration);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setPlatformViewClient(int i, boolean z) {
                TextInputPlugin.this.setPlatformViewTextInputClient(i, z);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setEditingState(TextInputChannel.TextEditState textEditState) {
                TextInputPlugin textInputPlugin = TextInputPlugin.this;
                textInputPlugin.setTextInputEditingState(textInputPlugin.mView, textEditState);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void setEditableSizeAndTransform(double d, double d2, double[] dArr) {
                TextInputPlugin.this.saveEditableSizeAndTransform(d, d2, dArr);
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void clearClient() {
                TextInputPlugin.this.clearTextInputClient();
            }

            @Override // io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputMethodHandler
            public void sendAppPrivateCommand(String str, Bundle bundle) {
                TextInputPlugin.this.sendTextInputAppPrivateCommand(str, bundle);
            }
        });
        textInputChannel.requestExistingInputState();
        this.scribeChannel = scribeChannel;
        this.platformViewsController = platformViewsController;
        platformViewsController.attachTextInputPlugin(this);
        this.platformViewsController2 = platformViewsController2;
        platformViewsController2.attachTextInputPlugin(this);
    }

    public InputMethodManager getInputMethodManager() {
        return this.mImm;
    }

    Editable getEditable() {
        return this.mEditable;
    }

    ImeSyncDeferringInsetsCallback getImeSyncCallback() {
        return this.imeSyncCallback;
    }

    public void lockPlatformViewInputConnection() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            this.isInputConnectionLocked = true;
        }
    }

    public void unlockPlatformViewInputConnection() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            this.isInputConnectionLocked = false;
        }
    }

    public void destroy() {
        this.platformViewsController.detachTextInputPlugin();
        this.platformViewsController2.detachTextInputPlugin();
        this.textInputChannel.setTextInputMethodHandler(null);
        notifyViewExited();
        this.mEditable.removeEditingStateListener(this);
        ImeSyncDeferringInsetsCallback imeSyncDeferringInsetsCallback = this.imeSyncCallback;
        if (imeSyncDeferringInsetsCallback != null) {
            imeSyncDeferringInsetsCallback.remove();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int inputTypeFromTextInputType(io.flutter.embedding.engine.systemchannels.TextInputChannel.InputType r1, boolean r2, boolean r3, boolean r4, boolean r5, io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization r6) {
        /*
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.DATETIME
            if (r5 != r0) goto L8
            r1 = 4
            return r1
        L8:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NUMBER
            if (r5 != r0) goto L1e
            boolean r2 = r1.isSigned
            if (r2 == 0) goto L15
            r2 = 4098(0x1002, float:5.743E-42)
            goto L16
        L15:
            r2 = 2
        L16:
            boolean r1 = r1.isDecimal
            if (r1 == 0) goto L1d
            r1 = r2 | 8192(0x2000, float:1.148E-41)
            return r1
        L1d:
            return r2
        L1e:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.PHONE
            if (r5 != r0) goto L26
            r1 = 3
            return r1
        L26:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NONE
            if (r5 != r0) goto L2e
            r1 = 0
            return r1
        L2e:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.MULTILINE
            if (r5 != r0) goto L38
            r1 = 131073(0x20001, float:1.83672E-40)
            goto L74
        L38:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.EMAIL_ADDRESS
            if (r5 == r0) goto L72
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.TWITTER
            if (r5 != r0) goto L45
            goto L72
        L45:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.URL
            if (r5 == r0) goto L6f
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.WEB_SEARCH
            if (r5 != r0) goto L52
            goto L6f
        L52:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.VISIBLE_PASSWORD
            if (r5 != r0) goto L5b
            r1 = 145(0x91, float:2.03E-43)
            goto L74
        L5b:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r0 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.NAME
            if (r5 != r0) goto L64
            r1 = 97
            goto L74
        L64:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r1 = r1.type
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextInputType r5 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextInputType.POSTAL_ADDRESS
            if (r1 != r5) goto L6d
            r1 = 113(0x71, float:1.58E-43)
            goto L74
        L6d:
            r1 = 1
            goto L74
        L6f:
            r1 = 17
            goto L74
        L72:
            r1 = 33
        L74:
            if (r2 == 0) goto L7b
            r2 = 524416(0x80080, float:7.34863E-40)
        L79:
            r1 = r1 | r2
            goto L87
        L7b:
            if (r3 == 0) goto L81
            r2 = 32768(0x8000, float:4.5918E-41)
            r1 = r1 | r2
        L81:
            if (r4 != 0) goto L87
            r2 = 524432(0x80090, float:7.34886E-40)
            goto L79
        L87:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.CHARACTERS
            if (r6 != r2) goto L8e
            r1 = r1 | 4096(0x1000, float:5.74E-42)
            return r1
        L8e:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.WORDS
            if (r6 != r2) goto L95
            r1 = r1 | 8192(0x2000, float:1.148E-41)
            return r1
        L95:
            io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization r2 = io.flutter.embedding.engine.systemchannels.TextInputChannel.TextCapitalization.SENTENCES
            if (r6 != r2) goto L9b
            r1 = r1 | 16384(0x4000, float:2.2959E-41)
        L9b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugin.editing.TextInputPlugin.inputTypeFromTextInputType(io.flutter.embedding.engine.systemchannels.TextInputChannel$InputType, boolean, boolean, boolean, boolean, io.flutter.embedding.engine.systemchannels.TextInputChannel$TextCapitalization):int");
    }

    public InputConnection createInputConnection(View view, KeyboardManager keyboardManager, EditorInfo editorInfo) {
        int iIntValue;
        if (this.inputTarget.type == InputTarget.Type.NO_TARGET) {
            this.lastInputConnection = null;
            return null;
        }
        if (this.inputTarget.type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
            return null;
        }
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            if (this.isInputConnectionLocked) {
                return this.lastInputConnection;
            }
            InputConnection inputConnectionOnCreateInputConnection = this.platformViewsController.getPlatformViewById(this.inputTarget.id).onCreateInputConnection(editorInfo);
            this.lastInputConnection = inputConnectionOnCreateInputConnection;
            return inputConnectionOnCreateInputConnection;
        }
        editorInfo.inputType = inputTypeFromTextInputType(this.configuration.inputType, this.configuration.obscureText, this.configuration.autocorrect, this.configuration.enableSuggestions, this.configuration.enableIMEPersonalizedLearning, this.configuration.textCapitalization);
        editorInfo.imeOptions = 33554432;
        if (Build.VERSION.SDK_INT >= 26 && !this.configuration.enableIMEPersonalizedLearning) {
            editorInfo.imeOptions |= 16777216;
        }
        if (this.configuration.inputAction == null) {
            iIntValue = (131072 & editorInfo.inputType) != 0 ? 1 : 6;
        } else {
            iIntValue = this.configuration.inputAction.intValue();
        }
        if (this.configuration.actionLabel != null) {
            editorInfo.actionLabel = this.configuration.actionLabel;
            editorInfo.actionId = iIntValue;
        }
        editorInfo.imeOptions = iIntValue | editorInfo.imeOptions;
        if (this.configuration.hintLocales != null) {
            editorInfo.hintLocales = new LocaleList(this.configuration.hintLocales);
        }
        if (this.configuration.contentCommitMimeTypes != null) {
            EditorInfoCompat.setContentMimeTypes(editorInfo, this.configuration.contentCommitMimeTypes);
        }
        if (Build.VERSION.SDK_INT >= 34) {
            EditorInfoCompat.setStylusHandwritingEnabled(editorInfo, true);
        }
        InputConnectionAdaptor inputConnectionAdaptor = new InputConnectionAdaptor(view, this.inputTarget.id, this.textInputChannel, this.scribeChannel, keyboardManager, this.mEditable, editorInfo);
        editorInfo.initialSelStart = this.mEditable.getSelectionStart();
        editorInfo.initialSelEnd = this.mEditable.getSelectionEnd();
        this.lastInputConnection = inputConnectionAdaptor;
        return inputConnectionAdaptor;
    }

    public InputConnection getLastInputConnection() {
        return this.lastInputConnection;
    }

    public void clearPlatformViewClient(int i) {
        if ((this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW || this.inputTarget.type == InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) && this.inputTarget.id == i) {
            this.inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
            notifyViewExited();
            this.mImm.hideSoftInputFromWindow(this.mView.getApplicationWindowToken(), 0);
            this.mImm.restartInput(this.mView);
            this.mRestartInputPending = false;
        }
    }

    public void sendTextInputAppPrivateCommand(String str, Bundle bundle) {
        this.mImm.sendAppPrivateCommand(this.mView, str, bundle);
    }

    void showTextInput(View view) {
        TextInputChannel.Configuration configuration = this.configuration;
        if (configuration == null || configuration.inputType == null || this.configuration.inputType.type != TextInputChannel.TextInputType.NONE) {
            view.requestFocus();
            this.mImm.showSoftInput(view, 0);
        } else {
            hideTextInput(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideTextInput(View view) {
        notifyViewExited();
        this.mImm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    void setTextInputClient(int i, TextInputChannel.Configuration configuration) {
        notifyViewExited();
        this.configuration = configuration;
        this.inputTarget = new InputTarget(InputTarget.Type.FRAMEWORK_CLIENT, i);
        this.mEditable.removeEditingStateListener(this);
        this.mEditable = new ListenableEditingState(configuration.autofill != null ? configuration.autofill.editState : null, this.mView);
        updateAutofillConfigurationIfNeeded(configuration);
        this.mRestartInputPending = true;
        unlockPlatformViewInputConnection();
        this.lastClientRect = null;
        this.mEditable.addEditingStateListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlatformViewTextInputClient(int i, boolean z) {
        if (z) {
            this.mView.requestFocus();
            this.inputTarget = new InputTarget(InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW, i);
            this.mImm.restartInput(this.mView);
            this.mRestartInputPending = false;
            return;
        }
        this.inputTarget = new InputTarget(InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW, i);
        this.lastInputConnection = null;
    }

    private static boolean composingChanged(TextInputChannel.TextEditState textEditState, TextInputChannel.TextEditState textEditState2) {
        int i = textEditState.composingEnd - textEditState.composingStart;
        if (i != textEditState2.composingEnd - textEditState2.composingStart) {
            return true;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (textEditState.text.charAt(textEditState.composingStart + i2) != textEditState2.text.charAt(textEditState2.composingStart + i2)) {
                return true;
            }
        }
        return false;
    }

    void setTextInputEditingState(View view, TextInputChannel.TextEditState textEditState) {
        TextInputChannel.TextEditState textEditState2;
        if (!this.mRestartInputPending && (textEditState2 = this.mLastKnownFrameworkTextEditingState) != null && textEditState2.hasComposing()) {
            boolean zComposingChanged = composingChanged(this.mLastKnownFrameworkTextEditingState, textEditState);
            this.mRestartInputPending = zComposingChanged;
            if (zComposingChanged) {
                Log.i(TAG, "Composing region changed by the framework. Restarting the input method.");
            }
        }
        this.mLastKnownFrameworkTextEditingState = textEditState;
        this.mEditable.setEditingState(textEditState);
        if (this.mRestartInputPending) {
            this.mImm.restartInput(view);
            this.mRestartInputPending = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveEditableSizeAndTransform(double d, double d2, final double[] dArr) {
        final double[] dArr2 = new double[4];
        final boolean z = dArr[3] == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && dArr[7] == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && dArr[15] == 1.0d;
        double d3 = dArr[12];
        double d4 = dArr[15];
        double d5 = d3 / d4;
        dArr2[1] = d5;
        dArr2[0] = d5;
        double d6 = dArr[13] / d4;
        dArr2[3] = d6;
        dArr2[2] = d6;
        MinMax minMax = new MinMax() { // from class: io.flutter.plugin.editing.TextInputPlugin.2
            @Override // io.flutter.plugin.editing.TextInputPlugin.MinMax
            public void inspect(double d7, double d8) {
                double d9 = 1.0d;
                if (!z) {
                    double[] dArr3 = dArr;
                    d9 = 1.0d / (((dArr3[3] * d7) + (dArr3[7] * d8)) + dArr3[15]);
                }
                double[] dArr4 = dArr;
                double d10 = ((dArr4[0] * d7) + (dArr4[4] * d8) + dArr4[12]) * d9;
                double d11 = ((dArr4[1] * d7) + (dArr4[5] * d8) + dArr4[13]) * d9;
                double[] dArr5 = dArr2;
                if (d10 < dArr5[0]) {
                    dArr5[0] = d10;
                } else if (d10 > dArr5[1]) {
                    dArr5[1] = d10;
                }
                if (d11 < dArr5[2]) {
                    dArr5[2] = d11;
                } else if (d11 > dArr5[3]) {
                    dArr5[3] = d11;
                }
            }
        };
        minMax.inspect(d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        minMax.inspect(d, d2);
        minMax.inspect(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d2);
        float f = this.mView.getContext().getResources().getDisplayMetrics().density;
        Float fValueOf = Float.valueOf(f);
        double d7 = dArr2[0];
        fValueOf.getClass();
        double d8 = f;
        int i = (int) (d7 * d8);
        double d9 = dArr2[2];
        fValueOf.getClass();
        double d10 = dArr2[1];
        fValueOf.getClass();
        int iCeil = (int) Math.ceil(d10 * d8);
        double d11 = dArr2[3];
        fValueOf.getClass();
        this.lastClientRect = new Rect(i, (int) (d9 * d8), iCeil, (int) Math.ceil(d11 * d8));
    }

    void clearTextInputClient() {
        if (this.inputTarget.type == InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
            return;
        }
        this.mEditable.removeEditingStateListener(this);
        notifyViewExited();
        this.configuration = null;
        updateAutofillConfigurationIfNeeded(null);
        this.inputTarget = new InputTarget(InputTarget.Type.NO_TARGET, 0);
        unlockPlatformViewInputConnection();
        this.lastClientRect = null;
    }

    private static class InputTarget {
        int id;
        Type type;

        enum Type {
            NO_TARGET,
            FRAMEWORK_CLIENT,
            VIRTUAL_DISPLAY_PLATFORM_VIEW,
            PHYSICAL_DISPLAY_PLATFORM_VIEW
        }

        public InputTarget(Type type, int i) {
            this.type = type;
            this.id = i;
        }
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        InputConnection inputConnection;
        if (!getInputMethodManager().isAcceptingText() || (inputConnection = this.lastInputConnection) == null) {
            return false;
        }
        if (inputConnection instanceof InputConnectionAdaptor) {
            return ((InputConnectionAdaptor) inputConnection).handleKeyEvent(keyEvent);
        }
        return inputConnection.sendKeyEvent(keyEvent);
    }

    @Override // io.flutter.plugin.editing.ListenableEditingState.EditingStateWatcher
    public void didChangeEditingState(boolean z, boolean z2, boolean z3) {
        if (z) {
            notifyValueChanged(this.mEditable.toString());
        }
        int selectionStart = this.mEditable.getSelectionStart();
        int selectionEnd = this.mEditable.getSelectionEnd();
        int composingStart = this.mEditable.getComposingStart();
        int composingEnd = this.mEditable.getComposingEnd();
        ArrayList<TextEditingDelta> arrayListExtractBatchTextEditingDeltas = this.mEditable.extractBatchTextEditingDeltas();
        if (this.mLastKnownFrameworkTextEditingState != null && (!this.mEditable.toString().equals(this.mLastKnownFrameworkTextEditingState.text) || selectionStart != this.mLastKnownFrameworkTextEditingState.selectionStart || selectionEnd != this.mLastKnownFrameworkTextEditingState.selectionEnd || composingStart != this.mLastKnownFrameworkTextEditingState.composingStart || composingEnd != this.mLastKnownFrameworkTextEditingState.composingEnd)) {
            Log.v(TAG, "send EditingState to flutter: " + this.mEditable.toString());
            if (this.configuration.enableDeltaModel) {
                this.textInputChannel.updateEditingStateWithDeltas(this.inputTarget.id, arrayListExtractBatchTextEditingDeltas);
                this.mEditable.clearBatchDeltas();
            } else {
                this.textInputChannel.updateEditingState(this.inputTarget.id, this.mEditable.toString(), selectionStart, selectionEnd, composingStart, composingEnd);
                selectionStart = selectionStart;
                selectionEnd = selectionEnd;
                composingStart = composingStart;
                composingEnd = composingEnd;
            }
            this.mLastKnownFrameworkTextEditingState = new TextInputChannel.TextEditState(this.mEditable.toString(), selectionStart, selectionEnd, composingStart, composingEnd);
            return;
        }
        this.mEditable.clearBatchDeltas();
    }

    private boolean needsAutofill() {
        return this.autofillConfiguration != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyViewEntered() {
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || !needsAutofill()) {
            return;
        }
        String str = this.configuration.autofill.uniqueIdentifier;
        int[] iArr = new int[2];
        this.mView.getLocationOnScreen(iArr);
        Rect rect = new Rect(this.lastClientRect);
        rect.offset(iArr[0], iArr[1]);
        this.afm.notifyViewEntered(this.mView, str.hashCode(), rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyViewExited() {
        TextInputChannel.Configuration configuration;
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || (configuration = this.configuration) == null || configuration.autofill == null || !needsAutofill()) {
            return;
        }
        this.afm.notifyViewExited(this.mView, this.configuration.autofill.uniqueIdentifier.hashCode());
    }

    private void notifyValueChanged(String str) {
        if (Build.VERSION.SDK_INT < 26 || this.afm == null || !needsAutofill()) {
            return;
        }
        this.afm.notifyValueChanged(this.mView, this.configuration.autofill.uniqueIdentifier.hashCode(), AutofillValue.forText(str));
    }

    private void updateAutofillConfigurationIfNeeded(TextInputChannel.Configuration configuration) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (configuration == null || configuration.autofill == null) {
            this.autofillConfiguration = null;
            return;
        }
        TextInputChannel.Configuration[] configurationArr = configuration.fields;
        SparseArray<TextInputChannel.Configuration> sparseArray = new SparseArray<>();
        this.autofillConfiguration = sparseArray;
        if (configurationArr == null) {
            sparseArray.put(configuration.autofill.uniqueIdentifier.hashCode(), configuration);
            return;
        }
        for (TextInputChannel.Configuration configuration2 : configurationArr) {
            TextInputChannel.Configuration.Autofill autofill = configuration2.autofill;
            if (autofill != null) {
                this.autofillConfiguration.put(autofill.uniqueIdentifier.hashCode(), configuration2);
                this.afm.notifyValueChanged(this.mView, autofill.uniqueIdentifier.hashCode(), AutofillValue.forText(autofill.editState.text));
            }
        }
    }

    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        Rect rect;
        if (Build.VERSION.SDK_INT < 26 || !needsAutofill()) {
            return;
        }
        String str = this.configuration.autofill.uniqueIdentifier;
        AutofillId autofillId = viewStructure.getAutofillId();
        for (int i2 = 0; i2 < this.autofillConfiguration.size(); i2++) {
            int iKeyAt = this.autofillConfiguration.keyAt(i2);
            TextInputChannel.Configuration.Autofill autofill = this.autofillConfiguration.valueAt(i2).autofill;
            if (autofill != null) {
                viewStructure.addChildCount(1);
                ViewStructure viewStructureNewChild = viewStructure.newChild(i2);
                viewStructureNewChild.setAutofillId(autofillId, iKeyAt);
                if (autofill.hints.length > 0) {
                    viewStructureNewChild.setAutofillHints(autofill.hints);
                }
                viewStructureNewChild.setAutofillType(1);
                viewStructureNewChild.setVisibility(0);
                if (autofill.hintText != null) {
                    viewStructureNewChild.setHint(autofill.hintText);
                }
                if (str.hashCode() == iKeyAt && (rect = this.lastClientRect) != null) {
                    viewStructureNewChild.setDimens(rect.left, this.lastClientRect.top, 0, 0, this.lastClientRect.width(), this.lastClientRect.height());
                    viewStructureNewChild.setAutofillValue(AutofillValue.forText(this.mEditable));
                } else {
                    viewStructureNewChild.setDimens(0, 0, 0, 0, 1, 1);
                    viewStructureNewChild.setAutofillValue(AutofillValue.forText(autofill.editState.text));
                }
            }
        }
    }

    public void autofill(SparseArray<AutofillValue> sparseArray) {
        TextInputChannel.Configuration configuration;
        if (Build.VERSION.SDK_INT < 26 || (configuration = this.configuration) == null || this.autofillConfiguration == null || configuration.autofill == null) {
            return;
        }
        TextInputChannel.Configuration.Autofill autofill = this.configuration.autofill;
        HashMap<String, TextInputChannel.TextEditState> map = new HashMap<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            TextInputChannel.Configuration configuration2 = this.autofillConfiguration.get(sparseArray.keyAt(i));
            if (configuration2 != null && configuration2.autofill != null) {
                TextInputChannel.Configuration.Autofill autofill2 = configuration2.autofill;
                String string = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m459m((Object) sparseArray.valueAt(i)).getTextValue().toString();
                TextInputChannel.TextEditState textEditState = new TextInputChannel.TextEditState(string, string.length(), string.length(), -1, -1);
                if (autofill2.uniqueIdentifier.equals(autofill.uniqueIdentifier)) {
                    this.mEditable.setEditingState(textEditState);
                } else {
                    map.put(autofill2.uniqueIdentifier, textEditState);
                }
            }
        }
        this.textInputChannel.updateEditingStateWithTag(this.inputTarget.id, map);
    }
}
