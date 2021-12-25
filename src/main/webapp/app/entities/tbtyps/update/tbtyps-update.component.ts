import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBTYPS, TBTYPS } from '../tbtyps.model';
import { TBTYPSService } from '../service/tbtyps.service';

@Component({
  selector: 'jhi-tbtyps-update',
  templateUrl: './tbtyps-update.component.html',
})
export class TBTYPSUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    tpssts: [null, [Validators.required]],
    tpscod: [null, [Validators.required, Validators.maxLength(1)]],
    tpsnam: [null, [Validators.required, Validators.maxLength(40)]],
    tpslmd: [null, [Validators.required]],
    tpsuid: [null, [Validators.required]],
  });

  constructor(protected tBTYPSService: TBTYPSService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBTYPS }) => {
      this.updateForm(tBTYPS);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBTYPS = this.createFromForm();
    if (tBTYPS.tpscod !== undefined) {
      this.subscribeToSaveResponse(this.tBTYPSService.update(tBTYPS));
    } else {
      this.subscribeToSaveResponse(this.tBTYPSService.create(tBTYPS));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBTYPS>>): void {
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

  protected updateForm(tBTYPS: ITBTYPS): void {
    this.editForm.patchValue({
      tpssts: tBTYPS.tpssts,
      tpscod: tBTYPS.tpscod,
      tpsnam: tBTYPS.tpsnam,
      tpslmd: tBTYPS.tpslmd,
      tpsuid: tBTYPS.tpsuid,
    });
  }

  protected createFromForm(): ITBTYPS {
    return {
      ...new TBTYPS(),
      tpssts: this.editForm.get(['tpssts'])!.value,
      tpscod: this.editForm.get(['tpscod'])!.value,
      tpsnam: this.editForm.get(['tpsnam'])!.value,
      tpslmd: this.editForm.get(['tpslmd'])!.value,
      tpsuid: this.editForm.get(['tpsuid'])!.value,
    };
  }
}
