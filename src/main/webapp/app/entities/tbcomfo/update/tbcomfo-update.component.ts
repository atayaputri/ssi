import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBCOMFO, TBCOMFO } from '../tbcomfo.model';
import { TBCOMFOService } from '../service/tbcomfo.service';

@Component({
  selector: 'jhi-tbcomfo-update',
  templateUrl: './tbcomfo-update.component.html',
})
export class TBCOMFOUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    costs: [null, [Validators.required]],
    cocode: [null, [Validators.required, Validators.maxLength(10)]],
    conam: [null, [Validators.required, Validators.maxLength(40)]],
    cocbei: [null, [Validators.required, Validators.maxLength(6)]],
    conbei: [null, [Validators.required, Validators.maxLength(40)]],
    cosat: [null, [Validators.required, Validators.maxLength(15)]],
    conom: [null, [Validators.required]],
    coseri: [null, [Validators.required, Validators.maxLength(15)]],
    codir: [null, [Validators.required, Validators.maxLength(30)]],
    colmd: [null, [Validators.required]],
    couid: [null, [Validators.required]],
  });

  constructor(protected tBCOMFOService: TBCOMFOService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBCOMFO }) => {
      this.updateForm(tBCOMFO);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBCOMFO = this.createFromForm();
    if (tBCOMFO.id !== undefined) {
      this.subscribeToSaveResponse(this.tBCOMFOService.update(tBCOMFO));
    } else {
      this.subscribeToSaveResponse(this.tBCOMFOService.create(tBCOMFO));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBCOMFO>>): void {
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

  protected updateForm(tBCOMFO: ITBCOMFO): void {
    this.editForm.patchValue({
      id: tBCOMFO.id,
      costs: tBCOMFO.costs,
      cocode: tBCOMFO.cocode,
      conam: tBCOMFO.conam,
      cocbei: tBCOMFO.cocbei,
      conbei: tBCOMFO.conbei,
      cosat: tBCOMFO.cosat,
      conom: tBCOMFO.conom,
      coseri: tBCOMFO.coseri,
      codir: tBCOMFO.codir,
      colmd: tBCOMFO.colmd,
      couid: tBCOMFO.couid,
    });
  }

  protected createFromForm(): ITBCOMFO {
    return {
      ...new TBCOMFO(),
      id: this.editForm.get(['id'])!.value,
      costs: this.editForm.get(['costs'])!.value,
      cocode: this.editForm.get(['cocode'])!.value,
      conam: this.editForm.get(['conam'])!.value,
      cocbei: this.editForm.get(['cocbei'])!.value,
      conbei: this.editForm.get(['conbei'])!.value,
      cosat: this.editForm.get(['cosat'])!.value,
      conom: this.editForm.get(['conom'])!.value,
      coseri: this.editForm.get(['coseri'])!.value,
      codir: this.editForm.get(['codir'])!.value,
      colmd: this.editForm.get(['colmd'])!.value,
      couid: this.editForm.get(['couid'])!.value,
    };
  }
}
