package kotlinx.coroutines;

import com.google.firebase.remoteconfig.RemoteConfigConstants;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

/* compiled from: CompletionState.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0000¢\u0006\u0002\u0010\u0004\u001a+\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\u0010\u0007\u001a1\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u00022\b\u0010\t\u001a\u0004\u0018\u00010\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0000¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"toState", "", "T", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "recoverResult", RemoteConfigConstants.ResponseFieldKey.STATE, "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class CompletionStateKt {
    public static final <T> Object toState(Object obj) {
        Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(obj);
        return thM482exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM482exceptionOrNullimpl, false, 2, null);
    }

    public static final <T> Object toState(Object obj, CancellableContinuation<?> cancellableContinuation) {
        Throwable thM482exceptionOrNullimpl = Result.m482exceptionOrNullimpl(obj);
        if (thM482exceptionOrNullimpl == null) {
            return obj;
        }
        if (DebugKt.getRECOVER_STACK_TRACES()) {
            CancellableContinuation<?> cancellableContinuation2 = cancellableContinuation;
            if (cancellableContinuation2 instanceof CoroutineStackFrame) {
                thM482exceptionOrNullimpl = StackTraceRecoveryKt.recoverFromStackFrame(thM482exceptionOrNullimpl, (CoroutineStackFrame) cancellableContinuation2);
            }
        }
        return new CompletedExceptionally(thM482exceptionOrNullimpl, false, 2, null);
    }

    public static final <T> Object recoverResult(Object obj, Continuation<? super T> continuation) {
        if (obj instanceof CompletedExceptionally) {
            Result.Companion companion = Result.INSTANCE;
            Throwable thRecoverFromStackFrame = ((CompletedExceptionally) obj).cause;
            if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                thRecoverFromStackFrame = StackTraceRecoveryKt.recoverFromStackFrame(thRecoverFromStackFrame, (CoroutineStackFrame) continuation);
            }
            return Result.m479constructorimpl(ResultKt.createFailure(thRecoverFromStackFrame));
        }
        Result.Companion companion2 = Result.INSTANCE;
        return Result.m479constructorimpl(obj);
    }
}
