package com.google.android.gms.internal.measurement;

import java.util.List;
import org.apache.tika.mime.MimeTypesReaderMetKeys;

/* compiled from: com.google.android.gms:play-services-measurement@@23.0.0 */
/* loaded from: classes.dex */
public final class zzv extends zzai {
    private final zzz zza;

    public zzv(zzz zzzVar) {
        super("internal.registerCallback");
        this.zza = zzzVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzai
    public final zzao zza(zzg zzgVar, List list) {
        zzh.zza(this.zzd, 3, list);
        String strZzc = zzgVar.zza((zzao) list.get(0)).zzc();
        zzao zzaoVarZza = zzgVar.zza((zzao) list.get(1));
        if (!(zzaoVarZza instanceof zzan)) {
            throw new IllegalArgumentException("Invalid callback type");
        }
        zzao zzaoVarZza2 = zzgVar.zza((zzao) list.get(2));
        if (!(zzaoVarZza2 instanceof zzal)) {
            throw new IllegalArgumentException("Invalid callback params");
        }
        zzal zzalVar = (zzal) zzaoVarZza2;
        if (!zzalVar.zzj("type")) {
            throw new IllegalArgumentException("Undefined rule type");
        }
        this.zza.zza(strZzc, zzalVar.zzj(MimeTypesReaderMetKeys.MAGIC_PRIORITY_ATTR) ? zzh.zzg(zzalVar.zzk(MimeTypesReaderMetKeys.MAGIC_PRIORITY_ATTR).zzd().doubleValue()) : 1000, (zzan) zzaoVarZza, zzalVar.zzk("type").zzc());
        return zzao.zzf;
    }
}
