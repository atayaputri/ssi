import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBJNSHM, TBJNSHM } from '../tbjnshm.model';
import { TBJNSHMService } from '../service/tbjnshm.service';

@Component({
  selector: 'jhi-tbjnshm-update',
  templateUrl: './tbjnshm-update.component.html',
})
export class TBJNSHMUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    jshsts: [null, [Validators.required]],
    jshcod: [null, [Validators.required, Validators.maxLength(1)]],
    jshnam: [null, [Validators.required, Validators.maxLength(40)]],
    jshlmd: [null, [Validators.required]],
    jshuid: [null, [Validators.required]],
  });

  constructor(protected tBJNSHMService: TBJNSHMService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBJNSHM }) => {
      this.updateForm(tBJNSHM);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBJNSHM = this.createFromForm();
    if (tBJNSHM.jshcod !== undefined) {
      this.subscribeToSaveResponse(this.tBJNSHMService.update(tBJNSHM));
    } else {
      this.subscribeToSaveResponse(this.tBJNSHMService.create(tBJNSHM));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBJNSHM>>): void {
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

  protected updateForm(tBJNSHM: ITBJNSHM): void {
    this.editForm.patchValue({
      jshsts: tBJNSHM.jshsts,
      jshcod: tBJNSHM.jshcod,
      jshnam: tBJNSHM.jshnam,
      jshlmd: tBJNSHM.jshlmd,
      jshuid: tBJNSHM.jshuid,
    });
  }

  protected createFromForm(): ITBJNSHM {
    return {
      ...new TBJNSHM(),
      jshsts: this.editForm.get(['jshsts'])!.value,
      jshcod: this.editForm.get(['jshcod'])!.value,
      jshnam: this.editForm.get(['jshnam'])!.value,
      jshlmd: this.editForm.get(['jshlmd'])!.value,
      jshuid: this.editForm.get(['jshuid'])!.value,
    };
  }
}
