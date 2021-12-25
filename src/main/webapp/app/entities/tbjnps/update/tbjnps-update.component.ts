import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBJNPS, TBJNPS } from '../tbjnps.model';
import { TBJNPSService } from '../service/tbjnps.service';

@Component({
  selector: 'jhi-tbjnps-update',
  templateUrl: './tbjnps-update.component.html',
})
export class TBJNPSUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    jpssts: [null, [Validators.required]],
    jpscod: [null, [Validators.required, Validators.maxLength(1)]],
    jpsnam: [null, [Validators.required, Validators.maxLength(40)]],
    jpslmd: [null, [Validators.required]],
    jpsuid: [null, [Validators.required]],
  });

  constructor(protected tBJNPSService: TBJNPSService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBJNPS }) => {
      this.updateForm(tBJNPS);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBJNPS = this.createFromForm();
    if (tBJNPS.jpscod !== undefined) {
      this.subscribeToSaveResponse(this.tBJNPSService.update(tBJNPS));
    } else {
      this.subscribeToSaveResponse(this.tBJNPSService.create(tBJNPS));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBJNPS>>): void {
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

  protected updateForm(tBJNPS: ITBJNPS): void {
    this.editForm.patchValue({
      jpssts: tBJNPS.jpssts,
      jpscod: tBJNPS.jpscod,
      jpsnam: tBJNPS.jpsnam,
      jpslmd: tBJNPS.jpslmd,
      jpsuid: tBJNPS.jpsuid,
    });
  }

  protected createFromForm(): ITBJNPS {
    return {
      ...new TBJNPS(),
      jpssts: this.editForm.get(['jpssts'])!.value,
      jpscod: this.editForm.get(['jpscod'])!.value,
      jpsnam: this.editForm.get(['jpsnam'])!.value,
      jpslmd: this.editForm.get(['jpslmd'])!.value,
      jpsuid: this.editForm.get(['jpsuid'])!.value,
    };
  }
}
