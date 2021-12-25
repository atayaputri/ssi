import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBPART, TBPART } from '../tbpart.model';
import { TBPARTService } from '../service/tbpart.service';

@Component({
  selector: 'jhi-tbpart-update',
  templateUrl: './tbpart-update.component.html',
})
export class TBPARTUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    tpasts: [null, [Validators.required]],
    tpacode: [null, [Validators.required, Validators.maxLength(10)]],
    tpanam: [null, [Validators.required, Validators.maxLength(40)]],
    tparek: [null, [Validators.required, Validators.maxLength(30)]],
    tpadis: [null, [Validators.required]],
    tpalmd: [null, [Validators.required]],
    tpauid: [null, [Validators.required]],
  });

  constructor(protected tBPARTService: TBPARTService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBPART }) => {
      this.updateForm(tBPART);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBPART = this.createFromForm();
    if (tBPART.tpacode !== undefined) {
      this.subscribeToSaveResponse(this.tBPARTService.update(tBPART));
    } else {
      this.subscribeToSaveResponse(this.tBPARTService.create(tBPART));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBPART>>): void {
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

  protected updateForm(tBPART: ITBPART): void {
    this.editForm.patchValue({
      tpasts: tBPART.tpasts,
      tpacode: tBPART.tpacode,
      tpanam: tBPART.tpanam,
      tparek: tBPART.tparek,
      tpadis: tBPART.tpadis,
      tpalmd: tBPART.tpalmd,
      tpauid: tBPART.tpauid,
    });
  }

  protected createFromForm(): ITBPART {
    return {
      ...new TBPART(),
      tpasts: this.editForm.get(['tpasts'])!.value,
      tpacode: this.editForm.get(['tpacode'])!.value,
      tpanam: this.editForm.get(['tpanam'])!.value,
      tparek: this.editForm.get(['tparek'])!.value,
      tpadis: this.editForm.get(['tpadis'])!.value,
      tpalmd: this.editForm.get(['tpalmd'])!.value,
      tpauid: this.editForm.get(['tpauid'])!.value,
    };
  }
}
