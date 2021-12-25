import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMFSHM, MFSHM } from '../mfshm.model';
import { MFSHMService } from '../service/mfshm.service';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { MFSKSService } from 'app/entities/mfsks/service/mfsks.service';

@Component({
  selector: 'jhi-mfshm-update',
  templateUrl: './mfshm-update.component.html',
})
export class MFSHMUpdateComponent implements OnInit {
  isSaving = false;

  mFSKSSharedCollection: IMFSKS[] = [];

  editForm = this.fb.group({
    id: [],
    shsts: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
    shfr: [null, [Validators.required]],
    shto: [null, [Validators.required]],
    shjshm: [null, [Validators.required]],
    shbat: [null, [Validators.required]],
    shseq: [null, [Validators.required]],
    shref: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    shdis: [null, [Validators.required]],
    shlmd: [null, [Validators.required]],
    shuid: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
    shsks: [],
  });

  constructor(
    protected mFSHMService: MFSHMService,
    protected mFSKSService: MFSKSService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFSHM }) => {
      this.updateForm(mFSHM);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mFSHM = this.createFromForm();
    if (mFSHM.id !== undefined) {
      this.subscribeToSaveResponse(this.mFSHMService.update(mFSHM));
    } else {
      this.subscribeToSaveResponse(this.mFSHMService.create(mFSHM));
    }
  }

  trackMFSKSBySkno(index: number, item: IMFSKS): number {
    return item.skno!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMFSHM>>): void {
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

  protected updateForm(mFSHM: IMFSHM): void {
    this.editForm.patchValue({
      id: mFSHM.id,
      shsts: mFSHM.shsts,
      shfr: mFSHM.shfr,
      shto: mFSHM.shto,
      shjshm: mFSHM.shjshm,
      shbat: mFSHM.shbat,
      shseq: mFSHM.shseq,
      shref: mFSHM.shref,
      shdis: mFSHM.shdis,
      shlmd: mFSHM.shlmd,
      shuid: mFSHM.shuid,
      shsks: mFSHM.shsks,
    });

    this.mFSKSSharedCollection = this.mFSKSService.addMFSKSToCollectionIfMissing(this.mFSKSSharedCollection, mFSHM.shsks);
  }

  protected loadRelationshipsOptions(): void {
    this.mFSKSService
      .query()
      .pipe(map((res: HttpResponse<IMFSKS[]>) => res.body ?? []))
      .pipe(map((mFSKS: IMFSKS[]) => this.mFSKSService.addMFSKSToCollectionIfMissing(mFSKS, this.editForm.get('shsks')!.value)))
      .subscribe((mFSKS: IMFSKS[]) => (this.mFSKSSharedCollection = mFSKS));
  }

  protected createFromForm(): IMFSHM {
    return {
      ...new MFSHM(),
      id: this.editForm.get(['id'])!.value,
      shsts: this.editForm.get(['shsts'])!.value,
      shfr: this.editForm.get(['shfr'])!.value,
      shto: this.editForm.get(['shto'])!.value,
      shjshm: this.editForm.get(['shjshm'])!.value,
      shbat: this.editForm.get(['shbat'])!.value,
      shseq: this.editForm.get(['shseq'])!.value,
      shref: this.editForm.get(['shref'])!.value,
      shdis: this.editForm.get(['shdis'])!.value,
      shlmd: this.editForm.get(['shlmd'])!.value,
      shuid: this.editForm.get(['shuid'])!.value,
      shsks: this.editForm.get(['shsks'])!.value,
    };
  }
}
