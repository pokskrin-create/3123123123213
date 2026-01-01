package org.apache.tika.embedder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlinx.coroutines.scheduling.WorkQueueKt;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.external.ExternalParser;

/* loaded from: classes4.dex */
public class ExternalEmbedder implements Embedder {
    public static final String METADATA_COMMAND_ARGUMENTS_SERIALIZED_TOKEN = "${METADATA_SERIALIZED}";
    public static final String METADATA_COMMAND_ARGUMENTS_TOKEN = "${METADATA}";
    private static final long serialVersionUID = -2828829275642475697L;
    private final TemporaryResources tmp = new TemporaryResources();
    private Set<MediaType> supportedEmbedTypes = Collections.EMPTY_SET;
    private Map<Property, String[]> metadataCommandArguments = null;
    private String[] command = {"sed", "-e", "$a\\\n${METADATA_SERIALIZED}", ExternalParser.INPUT_FILE_TOKEN};
    private String commandAssignmentOperator = "=";
    private String commandAssignmentDelimeter = ", ";
    private String commandAppendOperator = "=";
    private boolean quoteAssignmentValues = false;

    protected static String serializeMetadata(List<String> list) {
        if (list != null) {
            return Arrays.toString(list.toArray());
        }
        return "";
    }

    public static boolean check(String str, int... iArr) {
        return check(new String[]{str}, iArr);
    }

    public static boolean check(String[] strArr, int... iArr) throws InterruptedException, IOException {
        Process processExec;
        if (iArr.length == 0) {
            iArr = new int[]{WorkQueueKt.MASK};
        }
        try {
            if (strArr.length == 1) {
                processExec = Runtime.getRuntime().exec(strArr[0]);
            } else {
                processExec = Runtime.getRuntime().exec(strArr);
            }
            int iWaitFor = processExec.waitFor();
            for (int i : iArr) {
                if (iWaitFor == i) {
                    return false;
                }
            }
            return true;
        } catch (IOException | InterruptedException unused) {
            return false;
        }
    }

    @Override // org.apache.tika.embedder.Embedder
    public Set<MediaType> getSupportedEmbedTypes(ParseContext parseContext) {
        return getSupportedEmbedTypes();
    }

    public Set<MediaType> getSupportedEmbedTypes() {
        return this.supportedEmbedTypes;
    }

    public void setSupportedEmbedTypes(Set<MediaType> set) {
        this.supportedEmbedTypes = Collections.unmodifiableSet(new HashSet(set));
    }

    public String[] getCommand() {
        return this.command;
    }

    public void setCommand(String... strArr) {
        this.command = strArr;
    }

    public String getCommandAssignmentOperator() {
        return this.commandAssignmentOperator;
    }

    public void setCommandAssignmentOperator(String str) {
        this.commandAssignmentOperator = str;
    }

    public String getCommandAssignmentDelimeter() {
        return this.commandAssignmentDelimeter;
    }

    public void setCommandAssignmentDelimeter(String str) {
        this.commandAssignmentDelimeter = str;
    }

    public String getCommandAppendOperator() {
        return this.commandAppendOperator;
    }

    public void setCommandAppendOperator(String str) {
        this.commandAppendOperator = str;
    }

    public boolean isQuoteAssignmentValues() {
        return this.quoteAssignmentValues;
    }

    public void setQuoteAssignmentValues(boolean z) {
        this.quoteAssignmentValues = z;
    }

    public Map<Property, String[]> getMetadataCommandArguments() {
        return this.metadataCommandArguments;
    }

    public void setMetadataCommandArguments(Map<Property, String[]> map) {
        this.metadataCommandArguments = map;
    }

    protected List<String> getCommandMetadataSegments(Metadata metadata) {
        String[] strArr;
        String[] strArr2;
        int i;
        ArrayList arrayList = new ArrayList();
        if (metadata != null && metadata.names() != null) {
            String[] strArrNames = metadata.names();
            int length = strArrNames.length;
            for (int i2 = 0; i2 < length; i2++) {
                String str = strArrNames[i2];
                for (Property property : getMetadataCommandArguments().keySet()) {
                    if (str.equals(property.getName()) && (strArr = getMetadataCommandArguments().get(property)) != null) {
                        int length2 = strArr.length;
                        int i3 = 0;
                        while (i3 < length2) {
                            String str2 = strArr[i3];
                            if (metadata.isMultiValued(str)) {
                                String[] values = metadata.getValues(str);
                                int length3 = values.length;
                                int i4 = 0;
                                while (true) {
                                    strArr2 = strArrNames;
                                    if (i4 >= length3) {
                                        break;
                                    }
                                    String str3 = values[i4];
                                    int i5 = length;
                                    if (this.quoteAssignmentValues) {
                                        str3 = "'" + str3 + "'";
                                    }
                                    arrayList.add(str2 + this.commandAppendOperator + str3);
                                    i4++;
                                    strArrNames = strArr2;
                                    length = i5;
                                }
                                i = length;
                            } else {
                                strArr2 = strArrNames;
                                i = length;
                                String str4 = metadata.get(str);
                                if (this.quoteAssignmentValues) {
                                    str4 = "'" + str4 + "'";
                                }
                                arrayList.add(str2 + this.commandAssignmentOperator + str4);
                            }
                            i3++;
                            strArrNames = strArr2;
                            length = i;
                        }
                    }
                    strArrNames = strArrNames;
                    length = length;
                }
            }
        }
        return arrayList;
    }

    @Override // org.apache.tika.embedder.Embedder
    public void embed(Metadata metadata, InputStream inputStream, OutputStream outputStream, ParseContext parseContext) throws TikaException, InterruptedException, IOException {
        Process processExec;
        TikaInputStream tikaInputStream;
        Map<Property, String[]> map = this.metadataCommandArguments;
        boolean z = (map == null || map.isEmpty()) ? false : true;
        TikaInputStream tikaInputStream2 = TikaInputStream.get(inputStream);
        File file = null;
        List<String> commandMetadataSegments = z ? getCommandMetadataSegments(metadata) : null;
        String[] strArr = this.command;
        ArrayList<String> arrayList = new ArrayList();
        int length = strArr.length;
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = true;
        boolean z5 = true;
        while (i < length) {
            String strReplace = strArr[i];
            boolean z6 = z;
            if (strReplace.contains(ExternalParser.INPUT_FILE_TOKEN)) {
                tikaInputStream = tikaInputStream2;
                strReplace = strReplace.replace(ExternalParser.INPUT_FILE_TOKEN, tikaInputStream2.getFile().toString());
                z4 = false;
            } else {
                tikaInputStream = tikaInputStream2;
            }
            if (strReplace.contains(ExternalParser.OUTPUT_FILE_TOKEN)) {
                File fileCreateTemporaryFile = this.tmp.createTemporaryFile();
                strReplace = strReplace.replace(ExternalParser.OUTPUT_FILE_TOKEN, fileCreateTemporaryFile.toString());
                file = fileCreateTemporaryFile;
                z5 = false;
            }
            if (strReplace.contains(METADATA_COMMAND_ARGUMENTS_SERIALIZED_TOKEN)) {
                z2 = true;
            }
            if (strReplace.contains(METADATA_COMMAND_ARGUMENTS_TOKEN)) {
                if (z6) {
                    arrayList.addAll(commandMetadataSegments);
                }
                z3 = true;
            } else {
                arrayList.add(strReplace);
            }
            i++;
            z = z6;
            tikaInputStream2 = tikaInputStream;
        }
        TikaInputStream tikaInputStream3 = tikaInputStream2;
        if (z) {
            if (z2) {
                int i2 = 0;
                for (String str : arrayList) {
                    if (str.contains(METADATA_COMMAND_ARGUMENTS_SERIALIZED_TOKEN)) {
                        arrayList.set(i2, str.replace(METADATA_COMMAND_ARGUMENTS_SERIALIZED_TOKEN, serializeMetadata(commandMetadataSegments)));
                    }
                    i2++;
                }
            } else if (!z3 && !z2) {
                arrayList.addAll(commandMetadataSegments);
            }
        }
        if (arrayList.toArray().length == 1) {
            processExec = Runtime.getRuntime().exec(((String[]) arrayList.toArray(new String[0]))[0]);
        } else {
            processExec = Runtime.getRuntime().exec((String[]) arrayList.toArray(new String[0]));
        }
        Process process = processExec;
        UnsynchronizedByteArrayOutputStream unsynchronizedByteArrayOutputStream = UnsynchronizedByteArrayOutputStream.builder().get();
        try {
            sendStdErrToOutputStream(process, unsynchronizedByteArrayOutputStream);
            if (z4) {
                sendInputStreamToStdIn(inputStream, process);
            } else {
                process.getOutputStream().close();
            }
            if (z5) {
                sendStdOutToOutputStream(process, outputStream);
            } else {
                this.tmp.dispose();
                try {
                    process.waitFor();
                } catch (InterruptedException unused) {
                }
                IOUtils.copy(TikaInputStream.get(file), outputStream);
            }
            if (z5) {
                try {
                    process.waitFor();
                } catch (InterruptedException | Exception unused2) {
                }
            } else {
                file.delete();
            }
            if (!z4) {
                IOUtils.closeQuietly((InputStream) tikaInputStream3);
            }
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly((OutputStream) unsynchronizedByteArrayOutputStream);
            if (process.exitValue() == 0) {
                return;
            }
            throw new TikaException("There was an error executing the command line\nExecutable Command:\n\n" + arrayList + "\nExecutable Error:\n\n" + unsynchronizedByteArrayOutputStream.toString(StandardCharsets.UTF_8.name()));
        } catch (Throwable th) {
            if (z5) {
                try {
                    process.waitFor();
                } catch (InterruptedException | Exception unused3) {
                }
            } else {
                file.delete();
            }
            if (!z4) {
                IOUtils.closeQuietly((InputStream) tikaInputStream3);
            }
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly((OutputStream) unsynchronizedByteArrayOutputStream);
            if (process.exitValue() != 0) {
                throw new TikaException("There was an error executing the command line\nExecutable Command:\n\n" + arrayList + "\nExecutable Error:\n\n" + unsynchronizedByteArrayOutputStream.toString(StandardCharsets.UTF_8.name()));
            }
            throw th;
        }
    }

    private void multiThreadedStreamCopy(final InputStream inputStream, final OutputStream outputStream) {
        new Thread(new Runnable() { // from class: org.apache.tika.embedder.ExternalEmbedder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExternalEmbedder.lambda$multiThreadedStreamCopy$0(inputStream, outputStream);
            }
        }).start();
    }

    static /* synthetic */ void lambda$multiThreadedStreamCopy$0(InputStream inputStream, OutputStream outputStream) {
        try {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void sendInputStreamToStdIn(InputStream inputStream, Process process) {
        multiThreadedStreamCopy(inputStream, process.getOutputStream());
    }

    private void sendStdOutToOutputStream(Process process, OutputStream outputStream) {
        try {
            IOUtils.copy(process.getInputStream(), outputStream);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void sendStdErrToOutputStream(Process process, OutputStream outputStream) {
        multiThreadedStreamCopy(process.getErrorStream(), outputStream);
    }
}
