import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBBROKER, TBBROKER } from '../tbbroker.model';
import { TBBROKERService } from '../service/tbbroker.service';

@Component({
  selector: 'jhi-tbbroker-update',
  templateUrl: './tbbroker-update.component.html',
})
export class TBBROKERUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    brsts: [null, [Validators.required]],
    brcode: [null, [Validators.required, Validators.maxLength(2)]],
    brnam: [null, [Validators.required, Validators.maxLength(40)]],
    brlmd: [null, [Validators.required]],
    bruid: [null, [Validators.required]],
  });

  constructor(protected tBBROKERService: TBBROKERService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBBROKER }) => {
      this.updateForm(tBBROKER);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBBROKER = this.createFromForm();
    if (tBBROKER.brcode !== undefined) {
      this.subscribeToSaveResponse(this.tBBROKERService.update(tBBROKER));
    } else {
      this.subscribeToSaveResponse(this.tBBROKERService.create(tBBROKER));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBBROKER>>): void {
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

  protected updateForm(tBBROKER: ITBBROKER): void {
    this.editForm.patchValue({
      brsts: tBBROKER.brsts,
      brcode: tBBROKER.brcode,
      brnam: tBBROKER.brnam,
      brlmd: tBBROKER.brlmd,
      bruid: tBBROKER.bruid,
    });
  }

  protected createFromForm(): ITBBROKER {
    return {
      ...new TBBROKER(),
      brsts: this.editForm.get(['brsts'])!.value,
      brcode: this.editForm.get(['brcode'])!.value,
      brnam: this.editForm.get(['brnam'])!.value,
      brlmd: this.editForm.get(['brlmd'])!.value,
      bruid: this.editForm.get(['bruid'])!.value,
    };
  }
}
