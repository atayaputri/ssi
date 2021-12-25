import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMFHDR, MFHDR } from '../mfhdr.model';
import { MFHDRService } from '../service/mfhdr.service';
import { ITBKAB } from 'app/entities/tbkab/tbkab.model';
import { TBKABService } from 'app/entities/tbkab/service/tbkab.service';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';
import { TBPROVService } from 'app/entities/tbprov/service/tbprov.service';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';
import { TBNEGService } from 'app/entities/tbneg/service/tbneg.service';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';
import { TBJNPSService } from 'app/entities/tbjnps/service/tbjnps.service';
import { ITBTYPS } from 'app/entities/tbtyps/tbtyps.model';
import { TBTYPSService } from 'app/entities/tbtyps/service/tbtyps.service';
import { HolderGroup } from 'app/entities/enumerations/holder-group.model';
import { Citizenship } from 'app/entities/enumerations/citizenship.model';

@Component({
  selector: 'jhi-mfhdr-update',
  templateUrl: './mfhdr-update.component.html',
})
export class MFHDRUpdateComponent implements OnInit {
  isSaving = false;
  holderGroupValues = Object.keys(HolderGroup);
  citizenshipValues = Object.keys(Citizenship);

  tBKABSSharedCollection: ITBKAB[] = [];
  tBPROVSSharedCollection: ITBPROV[] = [];
  tBNEGSSharedCollection: ITBNEG[] = [];
  tBJNPSSharedCollection: ITBJNPS[] = [];
  tBTYPSSharedCollection: ITBTYPS[] = [];

  editForm = this.fb.group({
    hdsts: [null, [Validators.required]],
    hdno: [null, [Validators.required]],
    hdsid: [null, [Validators.required, Validators.maxLength(20)]],
    hdnm1: [null, [Validators.required, Validators.maxLength(50)]],
    hdnm2: [null, [Validators.required, Validators.maxLength(50)]],
    hdal1: [null, [Validators.required, Validators.maxLength(40)]],
    hdal2: [null, [Validators.required, Validators.maxLength(40)]],
    hdjsh: [null, [Validators.required]],
    hdinco: [null, [Validators.required]],
    hdkwn: [null, [Validators.required]],
    hdktp: [null, [Validators.required, Validators.maxLength(20)]],
    hdnpwp: [null, [Validators.required, Validators.maxLength(20)]],
    hdsiup: [null, [Validators.required, Validators.maxLength(20)]],
    hdtax: [null, [Validators.required]],
    hddis: [null, [Validators.required]],
    hdlmd: [null, [Validators.required]],
    hduid: [null, [Validators.required]],
    hdkota: [],
    hdprov: [],
    hdneg: [],
    hdjnps: [],
    hdtyps: [],
  });

  constructor(
    protected mFHDRService: MFHDRService,
    protected tBKABService: TBKABService,
    protected tBPROVService: TBPROVService,
    protected tBNEGService: TBNEGService,
    protected tBJNPSService: TBJNPSService,
    protected tBTYPSService: TBTYPSService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFHDR }) => {
      this.updateForm(mFHDR);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mFHDR = this.createFromForm();
    if (mFHDR.hdno !== undefined) {
      this.subscribeToSaveResponse(this.mFHDRService.update(mFHDR));
    } else {
      this.subscribeToSaveResponse(this.mFHDRService.create(mFHDR));
    }
  }

  trackTBKABByKabcod(index: number, item: ITBKAB): string {
    return item.kabcod!;
  }

  trackTBPROVByProvcod(index: number, item: ITBPROV): string {
    return item.provcod!;
  }

  trackTBNEGByNegcod(index: number, item: ITBNEG): string {
    return item.negcod!;
  }

  trackTBJNPSByJpscod(index: number, item: ITBJNPS): string {
    return item.jpscod!;
  }

  trackTBTYPSByTpscod(index: number, item: ITBTYPS): string {
    return item.tpscod!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMFHDR>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mFHDR: IMFHDR): void {
    this.editForm.patchValue({
      hdsts: mFHDR.hdsts,
      hdno: mFHDR.hdno,
      hdsid: mFHDR.hdsid,
      hdnm1: mFHDR.hdnm1,
      hdnm2: mFHDR.hdnm2,
      hdal1: mFHDR.hdal1,
      hdal2: mFHDR.hdal2,
      hdjsh: mFHDR.hdjsh,
      hdinco: mFHDR.hdinco,
      hdkwn: mFHDR.hdkwn,
      hdktp: mFHDR.hdktp,
      hdnpwp: mFHDR.hdnpwp,
      hdsiup: mFHDR.hdsiup,
      hdtax: mFHDR.hdtax,
      hddis: mFHDR.hddis,
      hdlmd: mFHDR.hdlmd,
      hduid: mFHDR.hduid,
      hdkota: mFHDR.hdkota,
      hdprov: mFHDR.hdprov,
      hdneg: mFHDR.hdneg,
      hdjnps: mFHDR.hdjnps,
      hdtyps: mFHDR.hdtyps,
    });

    this.tBKABSSharedCollection = this.tBKABService.addTBKABToCollectionIfMissing(this.tBKABSSharedCollection, mFHDR.hdkota);
    this.tBPROVSSharedCollection = this.tBPROVService.addTBPROVToCollectionIfMissing(this.tBPROVSSharedCollection, mFHDR.hdprov);
    this.tBNEGSSharedCollection = this.tBNEGService.addTBNEGToCollectionIfMissing(this.tBNEGSSharedCollection, mFHDR.hdneg);
    this.tBJNPSSharedCollection = this.tBJNPSService.addTBJNPSToCollectionIfMissing(this.tBJNPSSharedCollection, mFHDR.hdjnps);
    this.tBTYPSSharedCollection = this.tBTYPSService.addTBTYPSToCollectionIfMissing(this.tBTYPSSharedCollection, mFHDR.hdtyps);
  }

  protected loadRelationshipsOptions(): void {
    this.tBKABService
      .query()
      .pipe(map((res: HttpResponse<ITBKAB[]>) => res.body ?? []))
      .pipe(map((tBKABS: ITBKAB[]) => this.tBKABService.addTBKABToCollectionIfMissing(tBKABS, this.editForm.get('hdkota')!.value)))
      .subscribe((tBKABS: ITBKAB[]) => (this.tBKABSSharedCollection = tBKABS));

    this.tBPROVService
      .query()
      .pipe(map((res: HttpResponse<ITBPROV[]>) => res.body ?? []))
      .pipe(map((tBPROVS: ITBPROV[]) => this.tBPROVService.addTBPROVToCollectionIfMissing(tBPROVS, this.editForm.get('hdprov')!.value)))
      .subscribe((tBPROVS: ITBPROV[]) => (this.tBPROVSSharedCollection = tBPROVS));

    this.tBNEGService
      .query()
      .pipe(map((res: HttpResponse<ITBNEG[]>) => res.body ?? []))
      .pipe(map((tBNEGS: ITBNEG[]) => this.tBNEGService.addTBNEGToCollectionIfMissing(tBNEGS, this.editForm.get('hdneg')!.value)))
      .subscribe((tBNEGS: ITBNEG[]) => (this.tBNEGSSharedCollection = tBNEGS));

    this.tBJNPSService
      .query()
      .pipe(map((res: HttpResponse<ITBJNPS[]>) => res.body ?? []))
      .pipe(map((tBJNPS: ITBJNPS[]) => this.tBJNPSService.addTBJNPSToCollectionIfMissing(tBJNPS, this.editForm.get('hdjnps')!.value)))
      .subscribe((tBJNPS: ITBJNPS[]) => (this.tBJNPSSharedCollection = tBJNPS));

    this.tBTYPSService
      .query()
      .pipe(map((res: HttpResponse<ITBTYPS[]>) => res.body ?? []))
      .pipe(map((tBTYPS: ITBTYPS[]) => this.tBTYPSService.addTBTYPSToCollectionIfMissing(tBTYPS, this.editForm.get('hdtyps')!.value)))
      .subscribe((tBTYPS: ITBTYPS[]) => (this.tBTYPSSharedCollection = tBTYPS));
  }

  protected createFromForm(): IMFHDR {
    return {
      ...new MFHDR(),
      hdsts: this.editForm.get(['hdsts'])!.value,
      hdno: this.editForm.get(['hdno'])!.value,
      hdsid: this.editForm.get(['hdsid'])!.value,
      hdnm1: this.editForm.get(['hdnm1'])!.value,
      hdnm2: this.editForm.get(['hdnm2'])!.value,
      hdal1: this.editForm.get(['hdal1'])!.value,
      hdal2: this.editForm.get(['hdal2'])!.value,
      hdjsh: this.editForm.get(['hdjsh'])!.value,
      hdinco: this.editForm.get(['hdinco'])!.value,
      hdkwn: this.editForm.get(['hdkwn'])!.value,
      hdktp: this.editForm.get(['hdktp'])!.value,
      hdnpwp: this.editForm.get(['hdnpwp'])!.value,
      hdsiup: this.editForm.get(['hdsiup'])!.value,
      hdtax: this.editForm.get(['hdtax'])!.value,
      hddis: this.editForm.get(['hddis'])!.value,
      hdlmd: this.editForm.get(['hdlmd'])!.value,
      hduid: this.editForm.get(['hduid'])!.value,
      hdkota: this.editForm.get(['hdkota'])!.value,
      hdprov: this.editForm.get(['hdprov'])!.value,
      hdneg: this.editForm.get(['hdneg'])!.value,
      hdjnps: this.editForm.get(['hdjnps'])!.value,
      hdtyps: this.editForm.get(['hdtyps'])!.value,
    };
  }
}
