import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMAPSKS, MAPSKS } from '../mapsks.model';
import { MAPSKSService } from '../service/mapsks.service';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { MFSKSService } from 'app/entities/mfsks/service/mfsks.service';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { MFHDRService } from 'app/entities/mfhdr/service/mfhdr.service';
import { StatusSKS } from 'app/entities/enumerations/status-sks.model';

@Component({
  selector: 'jhi-mapsks-update',
  templateUrl: './mapsks-update.component.html',
})
export class MAPSKSUpdateComponent implements OnInit {
  isSaving = false;
  statusSKSValues = Object.keys(StatusSKS);

  mFSKSSharedCollection: IMFSKS[] = [];
  mFHDRSSharedCollection: IMFHDR[] = [];

  editForm = this.fb.group({
    id: [],
    msksts: [null, [Validators.required]],
    mskno: [],
    mskohdr: [],
    mskhdr: [],
  });

  constructor(
    protected mAPSKSService: MAPSKSService,
    protected mFSKSService: MFSKSService,
    protected mFHDRService: MFHDRService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mAPSKS }) => {
      this.updateForm(mAPSKS);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mAPSKS = this.createFromForm();
    if (mAPSKS.id !== undefined) {
      this.subscribeToSaveResponse(this.mAPSKSService.update(mAPSKS));
    } else {
      this.subscribeToSaveResponse(this.mAPSKSService.create(mAPSKS));
    }
  }

  trackMFSKSBySkno(index: number, item: IMFSKS): number {
    return item.skno!;
  }

  trackMFHDRByHdno(index: number, item: IMFHDR): number {
    return item.hdno!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMAPSKS>>): void {
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

  protected updateForm(mAPSKS: IMAPSKS): void {
    this.editForm.patchValue({
      id: mAPSKS.id,
      msksts: mAPSKS.msksts,
      mskno: mAPSKS.mskno,
      mskohdr: mAPSKS.mskohdr,
      mskhdr: mAPSKS.mskhdr,
    });

    this.mFSKSSharedCollection = this.mFSKSService.addMFSKSToCollectionIfMissing(this.mFSKSSharedCollection, mAPSKS.mskno);
    this.mFHDRSSharedCollection = this.mFHDRService.addMFHDRToCollectionIfMissing(
      this.mFHDRSSharedCollection,
      mAPSKS.mskohdr,
      mAPSKS.mskhdr
    );
  }

  protected loadRelationshipsOptions(): void {
    this.mFSKSService
      .query()
      .pipe(map((res: HttpResponse<IMFSKS[]>) => res.body ?? []))
      .pipe(map((mFSKS: IMFSKS[]) => this.mFSKSService.addMFSKSToCollectionIfMissing(mFSKS, this.editForm.get('mskno')!.value)))
      .subscribe((mFSKS: IMFSKS[]) => (this.mFSKSSharedCollection = mFSKS));

    this.mFHDRService
      .query()
      .pipe(map((res: HttpResponse<IMFHDR[]>) => res.body ?? []))
      .pipe(
        map((mFHDRS: IMFHDR[]) =>
          this.mFHDRService.addMFHDRToCollectionIfMissing(mFHDRS, this.editForm.get('mskohdr')!.value, this.editForm.get('mskhdr')!.value)
        )
      )
      .subscribe((mFHDRS: IMFHDR[]) => (this.mFHDRSSharedCollection = mFHDRS));
  }

  protected createFromForm(): IMAPSKS {
    return {
      ...new MAPSKS(),
      id: this.editForm.get(['id'])!.value,
      msksts: this.editForm.get(['msksts'])!.value,
      mskno: this.editForm.get(['mskno'])!.value,
      mskohdr: this.editForm.get(['mskohdr'])!.value,
      mskhdr: this.editForm.get(['mskhdr'])!.value,
    };
  }
}
