package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@23.0.0 */
/* loaded from: classes.dex */
final class zzln implements zzov {
    private final zzlm zza;

    private zzln(zzlm zzlmVar) {
        byte[] bArr = zzmp.zzb;
        this.zza = zzlmVar;
        zzlmVar.zza = this;
    }

    public static zzln zza(zzlm zzlmVar) {
        zzln zzlnVar = zzlmVar.zza;
        return zzlnVar != null ? zzlnVar : new zzln(zzlmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzG(int i, List list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zzi(i, (zzlh) list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzM(int i, zzne zzneVar, Map map) throws IOException {
        for (Map.Entry entry : map.entrySet()) {
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            zzlmVar.zzr(zznf.zzc(zzneVar, entry.getKey(), entry.getValue()));
            zznf.zzb(zzlmVar, zzneVar, entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzb(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzc(int i, long j) throws IOException {
        this.zza.zze(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzd(int i, long j) throws IOException {
        this.zza.zzf(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zze(int i, float f) throws IOException {
        this.zza.zzd(i, Float.floatToRawIntBits(f));
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzf(int i, double d) throws IOException {
        this.zza.zzf(i, Double.doubleToRawLongBits(d));
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzg(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzh(int i, long j) throws IOException {
        this.zza.zze(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzj(int i, long j) throws IOException {
        this.zza.zzf(i, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzl(int i, boolean z) throws IOException {
        this.zza.zzg(i, z);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzm(int i, String str) throws IOException {
        this.zza.zzh(i, str);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzn(int i, zzlh zzlhVar) throws IOException {
        this.zza.zzi(i, zzlhVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzo(int i, int i2) throws IOException {
        this.zza.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzp(int i, int i2) throws IOException {
        this.zza.zzc(i, (i2 >> 31) ^ (i2 + i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzq(int i, long j) throws IOException {
        this.zza.zze(i, (j >> 63) ^ (j + j));
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzr(int i, Object obj, zznx zznxVar) throws IOException {
        this.zza.zzl(i, (zznm) obj, zznxVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzs(int i, Object obj, zznx zznxVar) throws IOException {
        zzlm zzlmVar = this.zza;
        zzlmVar.zza(i, 3);
        zznxVar.zzf((zznm) obj, zzlmVar.zza);
        zzlmVar.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    @Deprecated
    public final void zzt(int i) throws IOException {
        this.zza.zza(i, 3);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    @Deprecated
    public final void zzu(int i) throws IOException {
        this.zza.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzv(int i, Object obj) throws IOException {
        if (obj instanceof zzlh) {
            this.zza.zzn(i, (zzlh) obj);
        } else {
            this.zza.zzm(i, (zznm) obj);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzF(int i, List list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmx)) {
            while (i2 < list.size()) {
                this.zza.zzh(i, (String) list.get(i2));
                i2++;
            }
            return;
        }
        zzmx zzmxVar = (zzmx) list;
        while (i2 < list.size()) {
            Object objZzc = zzmxVar.zzc();
            if (objZzc instanceof String) {
                this.zza.zzh(i, (String) objZzc);
            } else {
                this.zza.zzi(i, (zzlh) objZzc);
            }
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzA(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzna)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzf(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzu(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzna zznaVar = (zzna) list;
        if (!z) {
            while (i2 < zznaVar.size()) {
                this.zza.zzf(i, zznaVar.zzc(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zznaVar.size(); i6++) {
            zznaVar.zzc(i6);
            i5 += 8;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zznaVar.size()) {
            zzlmVar2.zzu(zznaVar.zzc(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzH(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzc(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int iZzz = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzz += zzlm.zzz(((Integer) list.get(i3)).intValue());
            }
            zzlmVar.zzr(iZzz);
            while (i2 < list.size()) {
                zzlmVar.zzr(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                this.zza.zzc(i, zzmgVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int iZzz2 = 0;
        for (int i4 = 0; i4 < zzmgVar.size(); i4++) {
            iZzz2 += zzlm.zzz(zzmgVar.zzf(i4));
        }
        zzlmVar2.zzr(iZzz2);
        while (i2 < zzmgVar.size()) {
            zzlmVar2.zzr(zzmgVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzx(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzd(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzs(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                this.zza.zzd(i, zzmgVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzmgVar.size(); i6++) {
            zzmgVar.zzf(i6);
            i5 += 4;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zzmgVar.size()) {
            zzlmVar2.zzs(zzmgVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzz(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzna)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zze(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int iZzA = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzA += zzlm.zzA(((Long) list.get(i3)).longValue());
            }
            zzlmVar.zzr(iZzA);
            while (i2 < list.size()) {
                zzlmVar.zzt(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzna zznaVar = (zzna) list;
        if (!z) {
            while (i2 < zznaVar.size()) {
                this.zza.zze(i, zznaVar.zzc(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int iZzA2 = 0;
        for (int i4 = 0; i4 < zznaVar.size(); i4++) {
            iZzA2 += zzlm.zzA(zznaVar.zzc(i4));
        }
        zzlmVar2.zzr(iZzA2);
        while (i2 < zznaVar.size()) {
            zzlmVar2.zzt(zznaVar.zzc(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzE(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzky)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzg(i, ((Boolean) list.get(i2)).booleanValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Boolean) list.get(i4)).booleanValue();
                i3++;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzp(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : (byte) 0);
                i2++;
            }
            return;
        }
        zzky zzkyVar = (zzky) list;
        if (!z) {
            while (i2 < zzkyVar.size()) {
                this.zza.zzg(i, zzkyVar.zze(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzkyVar.size(); i6++) {
            zzkyVar.zze(i6);
            i5++;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zzkyVar.size()) {
            zzlmVar2.zzp(zzkyVar.zze(i2) ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzw(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzb(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int iZzA = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzA += zzlm.zzA(((Integer) list.get(i3)).intValue());
            }
            zzlmVar.zzr(iZzA);
            while (i2 < list.size()) {
                zzlmVar.zzq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                this.zza.zzb(i, zzmgVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int iZzA2 = 0;
        for (int i4 = 0; i4 < zzmgVar.size(); i4++) {
            iZzA2 += zzlm.zzA(zzmgVar.zzf(i4));
        }
        zzlmVar2.zzr(iZzA2);
        while (i2 < zzmgVar.size()) {
            zzlmVar2.zzq(zzmgVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzB(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzly)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzd(i, Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Float) list.get(i4)).floatValue();
                i3 += 4;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzs(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
                i2++;
            }
            return;
        }
        zzly zzlyVar = (zzly) list;
        if (!z) {
            while (i2 < zzlyVar.size()) {
                this.zza.zzd(i, Float.floatToRawIntBits(zzlyVar.zze(i2)));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzlyVar.size(); i6++) {
            zzlyVar.zze(i6);
            i5 += 4;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zzlyVar.size()) {
            zzlmVar2.zzs(Float.floatToRawIntBits(zzlyVar.zze(i2)));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzC(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzlo)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzf(i, Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Double) list.get(i4)).doubleValue();
                i3 += 8;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzu(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
                i2++;
            }
            return;
        }
        zzlo zzloVar = (zzlo) list;
        if (!z) {
            while (i2 < zzloVar.size()) {
                this.zza.zzf(i, Double.doubleToRawLongBits(zzloVar.zze(i2)));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzloVar.size(); i6++) {
            zzloVar.zze(i6);
            i5 += 8;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zzloVar.size()) {
            zzlmVar2.zzu(Double.doubleToRawLongBits(zzloVar.zze(i2)));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzI(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzd(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Integer) list.get(i4)).intValue();
                i3 += 4;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzs(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                this.zza.zzd(i, zzmgVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zzmgVar.size(); i6++) {
            zzmgVar.zzf(i6);
            i5 += 4;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zzmgVar.size()) {
            zzlmVar2.zzs(zzmgVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzJ(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzna)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzf(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                ((Long) list.get(i4)).longValue();
                i3 += 8;
            }
            zzlmVar.zzr(i3);
            while (i2 < list.size()) {
                zzlmVar.zzu(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzna zznaVar = (zzna) list;
        if (!z) {
            while (i2 < zznaVar.size()) {
                this.zza.zzf(i, zznaVar.zzc(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < zznaVar.size(); i6++) {
            zznaVar.zzc(i6);
            i5 += 8;
        }
        zzlmVar2.zzr(i5);
        while (i2 < zznaVar.size()) {
            zzlmVar2.zzu(zznaVar.zzc(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzD(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zzb(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int iZzA = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzA += zzlm.zzA(((Integer) list.get(i3)).intValue());
            }
            zzlmVar.zzr(iZzA);
            while (i2 < list.size()) {
                zzlmVar.zzq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                this.zza.zzb(i, zzmgVar.zzf(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int iZzA2 = 0;
        for (int i4 = 0; i4 < zzmgVar.size(); i4++) {
            iZzA2 += zzlm.zzA(zzmgVar.zzf(i4));
        }
        zzlmVar2.zzr(iZzA2);
        while (i2 < zzmgVar.size()) {
            zzlmVar2.zzq(zzmgVar.zzf(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzK(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzmg)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzlm zzlmVar = this.zza;
                    int iIntValue = ((Integer) list.get(i2)).intValue();
                    zzlmVar.zzc(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                    i2++;
                }
                return;
            }
            zzlm zzlmVar2 = this.zza;
            zzlmVar2.zza(i, 2);
            int iZzz = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                int iIntValue2 = ((Integer) list.get(i3)).intValue();
                iZzz += zzlm.zzz((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
            }
            zzlmVar2.zzr(iZzz);
            while (i2 < list.size()) {
                int iIntValue3 = ((Integer) list.get(i2)).intValue();
                zzlmVar2.zzr((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
                i2++;
            }
            return;
        }
        zzmg zzmgVar = (zzmg) list;
        if (!z) {
            while (i2 < zzmgVar.size()) {
                zzlm zzlmVar3 = this.zza;
                int iZzf = zzmgVar.zzf(i2);
                zzlmVar3.zzc(i, (iZzf >> 31) ^ (iZzf + iZzf));
                i2++;
            }
            return;
        }
        zzlm zzlmVar4 = this.zza;
        zzlmVar4.zza(i, 2);
        int iZzz2 = 0;
        for (int i4 = 0; i4 < zzmgVar.size(); i4++) {
            int iZzf2 = zzmgVar.zzf(i4);
            iZzz2 += zzlm.zzz((iZzf2 >> 31) ^ (iZzf2 + iZzf2));
        }
        zzlmVar4.zzr(iZzz2);
        while (i2 < zzmgVar.size()) {
            int iZzf3 = zzmgVar.zzf(i2);
            zzlmVar4.zzr((iZzf3 >> 31) ^ (iZzf3 + iZzf3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzL(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzna)) {
            if (!z) {
                while (i2 < list.size()) {
                    zzlm zzlmVar = this.zza;
                    long jLongValue = ((Long) list.get(i2)).longValue();
                    zzlmVar.zze(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                    i2++;
                }
                return;
            }
            zzlm zzlmVar2 = this.zza;
            zzlmVar2.zza(i, 2);
            int iZzA = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                long jLongValue2 = ((Long) list.get(i3)).longValue();
                iZzA += zzlm.zzA((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
            }
            zzlmVar2.zzr(iZzA);
            while (i2 < list.size()) {
                long jLongValue3 = ((Long) list.get(i2)).longValue();
                zzlmVar2.zzt((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
                i2++;
            }
            return;
        }
        zzna zznaVar = (zzna) list;
        if (!z) {
            while (i2 < zznaVar.size()) {
                zzlm zzlmVar3 = this.zza;
                long jZzc = zznaVar.zzc(i2);
                zzlmVar3.zze(i, (jZzc >> 63) ^ (jZzc + jZzc));
                i2++;
            }
            return;
        }
        zzlm zzlmVar4 = this.zza;
        zzlmVar4.zza(i, 2);
        int iZzA2 = 0;
        for (int i4 = 0; i4 < zznaVar.size(); i4++) {
            long jZzc2 = zznaVar.zzc(i4);
            iZzA2 += zzlm.zzA((jZzc2 >> 63) ^ (jZzc2 + jZzc2));
        }
        zzlmVar4.zzr(iZzA2);
        while (i2 < zznaVar.size()) {
            long jZzc3 = zznaVar.zzc(i2);
            zzlmVar4.zzt((jZzc3 >> 63) ^ (jZzc3 + jZzc3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzov
    public final void zzy(int i, List list, boolean z) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzna)) {
            if (!z) {
                while (i2 < list.size()) {
                    this.zza.zze(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            zzlm zzlmVar = this.zza;
            zzlmVar.zza(i, 2);
            int iZzA = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzA += zzlm.zzA(((Long) list.get(i3)).longValue());
            }
            zzlmVar.zzr(iZzA);
            while (i2 < list.size()) {
                zzlmVar.zzt(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        zzna zznaVar = (zzna) list;
        if (!z) {
            while (i2 < zznaVar.size()) {
                this.zza.zze(i, zznaVar.zzc(i2));
                i2++;
            }
            return;
        }
        zzlm zzlmVar2 = this.zza;
        zzlmVar2.zza(i, 2);
        int iZzA2 = 0;
        for (int i4 = 0; i4 < zznaVar.size(); i4++) {
            iZzA2 += zzlm.zzA(zznaVar.zzc(i4));
        }
        zzlmVar2.zzr(iZzA2);
        while (i2 < zznaVar.size()) {
            zzlmVar2.zzt(zznaVar.zzc(i2));
            i2++;
        }
    }
}
