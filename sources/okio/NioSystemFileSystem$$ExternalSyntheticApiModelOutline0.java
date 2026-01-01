package okio;

import java.nio.file.CopyOption;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.PathMatcher;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.spi.FileSystemProvider;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class NioSystemFileSystem$$ExternalSyntheticApiModelOutline0 {
    public static /* bridge */ /* synthetic */ Class m() {
        return FileAttributeView.class;
    }

    public static /* bridge */ /* synthetic */ CopyOption m(Object obj) {
        return (CopyOption) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ FileStore m2138m(Object obj) {
        return (FileStore) obj;
    }

    public static /* synthetic */ NoSuchFileException m(String str) {
        return new NoSuchFileException(str);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ OpenOption m2140m(Object obj) {
        return (OpenOption) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ PathMatcher m2141m(Object obj) {
        return (PathMatcher) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ StandardOpenOption m2144m(Object obj) {
        return (StandardOpenOption) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ AclFileAttributeView m2145m(Object obj) {
        return (AclFileAttributeView) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ DosFileAttributeView m2146m(Object obj) {
        return (DosFileAttributeView) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ DosFileAttributes m2147m(Object obj) {
        return (DosFileAttributes) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ FileAttributeView m2148m(Object obj) {
        return (FileAttributeView) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ FileTime m2149m(Object obj) {
        return (FileTime) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ PosixFileAttributeView m2151m(Object obj) {
        return (PosixFileAttributeView) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ PosixFileAttributes m2152m(Object obj) {
        return (PosixFileAttributes) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ UserPrincipal m2154m(Object obj) {
        return (UserPrincipal) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ FileSystemProvider m2155m(Object obj) {
        return (FileSystemProvider) obj;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m2160m(Object obj) {
        return obj instanceof StandardOpenOption;
    }

    public static /* bridge */ /* synthetic */ Class m$1() {
        return DosFileAttributeView.class;
    }

    public static /* bridge */ /* synthetic */ boolean m$1(Object obj) {
        return obj instanceof java.nio.file.Path;
    }

    public static /* bridge */ /* synthetic */ Class m$2() {
        return DosFileAttributes.class;
    }

    public static /* bridge */ /* synthetic */ Class m$3() {
        return PosixFileAttributes.class;
    }

    public static /* bridge */ /* synthetic */ Class m$4() {
        return AclFileAttributeView.class;
    }

    public static /* bridge */ /* synthetic */ Class m$5() {
        return FileVisitOption.class;
    }

    public static /* bridge */ /* synthetic */ Class m$6() {
        return PosixFileAttributeView.class;
    }
}
