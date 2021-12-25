import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMFSKS, MFSKS } from '../mfsks.model';
import { MFSKSService } from '../service/mfsks.service';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { MFHDRService } from 'app/entities/mfhdr/service/mfhdr.service';

@Component({
  selector: 'jhi-mfsks-update',
  templateUrl: './mfsks-update.component.html',
})
export class MFSKSUpdateComponent implements OnInit {
  isSaving = false;

  mFHDRSSharedCollection: IMFHDR[] = [];

  editForm = this.fb.group({
    sksts: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
    skno: [null, [Validators.required]],
    skjsh: [null, [Validators.required]],
    skbat: [null, [Validators.required]],
    skseq: [null, [Validators.required]],
    skref: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    skdis: [null, [Validators.required]],
    sklmd: [null, [Validators.required]],
    skuid: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
    skfil1: [],
    skfil2: [],
    skfil3: [null, [Validators.minLength(1), Validators.maxLength(30)]],
    skfil4: [null, [Validators.minLength(1), Validators.maxLength(30)]],
    skshdr: [],
  });

  constructor(
    protected mFSKSService: MFSKSService,
    protected mFHDRService: MFHDRService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFSKS }) => {
      this.updateForm(mFSKS);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mFSKS = this.createFromForm();
    if (mFSKS.skno !== undefined) {
      this.subscribeToSaveResponse(this.mFSKSService.update(mFSKS));
    } else {
      this.subscribeToSaveResponse(this.mFSKSService.create(mFSKS));
    }
  }

  trackMFHDRByHdno(index: number, item: IMFHDR): number {
    return item.hdno!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMFSKS>>): void {
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

  protected updateForm(mFSKS: IMFSKS): void {
    this.editForm.patchValue({
      sksts: mFSKS.sksts,
      skno: mFSKS.skno,
      skjsh: mFSKS.skjsh,
      skbat: mFSKS.skbat,
      skseq: mFSKS.skseq,
      skref: mFSKS.skref,
      skdis: mFSKS.skdis,
      sklmd: mFSKS.sklmd,
      skuid: mFSKS.skuid,
      skfil1: mFSKS.skfil1,
      skfil2: mFSKS.skfil2,
      skfil3: mFSKS.skfil3,
      skfil4: mFSKS.skfil4,
      skshdr: mFSKS.skshdr,
    });

    this.mFHDRSSharedCollection = this.mFHDRService.addMFHDRToCollectionIfMissing(this.mFHDRSSharedCollection, mFSKS.skshdr);
  }

  protected loadRelationshipsOptions(): void {
    this.mFHDRService
      .query()
      .pipe(map((res: HttpResponse<IMFHDR[]>) => res.body ?? []))
      .pipe(map((mFHDRS: IMFHDR[]) => this.mFHDRService.addMFHDRToCollectionIfMissing(mFHDRS, this.editForm.get('skshdr')!.value)))
      .subscribe((mFHDRS: IMFHDR[]) => (this.mFHDRSSharedCollection = mFHDRS));
  }

  protected createFromForm(): IMFSKS {
    return {
      ...new MFSKS(),
      sksts: this.editForm.get(['sksts'])!.value,
      skno: this.editForm.get(['skno'])!.value,
      skjsh: this.editForm.get(['skjsh'])!.value,
      skbat: this.editForm.get(['skbat'])!.value,
      skseq: this.editForm.get(['skseq'])!.value,
      skref: this.editForm.get(['skref'])!.value,
      skdis: this.editForm.get(['skdis'])!.value,
      sklmd: this.editForm.get(['sklmd'])!.value,
      skuid: this.editForm.get(['skuid'])!.value,
      skfil1: this.editForm.get(['skfil1'])!.value,
      skfil2: this.editForm.get(['skfil2'])!.value,
      skfil3: this.editForm.get(['skfil3'])!.value,
      skfil4: this.editForm.get(['skfil4'])!.value,
      skshdr: this.editForm.get(['skshdr'])!.value,
    };
  }
}
