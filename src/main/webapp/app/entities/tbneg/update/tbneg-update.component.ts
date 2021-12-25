import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITBNEG, TBNEG } from '../tbneg.model';
import { TBNEGService } from '../service/tbneg.service';

@Component({
  selector: 'jhi-tbneg-update',
  templateUrl: './tbneg-update.component.html',
})
export class TBNEGUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    negsts: [null, [Validators.required]],
    negcod: [null, [Validators.required, Validators.maxLength(4)]],
    negnam: [null, [Validators.required, Validators.maxLength(40)]],
    negtax: [null, [Validators.required]],
    neglmd: [null, [Validators.required]],
    neguid: [null, [Validators.required]],
  });

  constructor(protected tBNEGService: TBNEGService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBNEG }) => {
      this.updateForm(tBNEG);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBNEG = this.createFromForm();
    if (tBNEG.negcod !== undefined) {
      this.subscribeToSaveResponse(this.tBNEGService.update(tBNEG));
    } else {
      this.subscribeToSaveResponse(this.tBNEGService.create(tBNEG));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBNEG>>): void {
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

  protected updateForm(tBNEG: ITBNEG): void {
    this.editForm.patchValue({
      negsts: tBNEG.negsts,
      negcod: tBNEG.negcod,
      negnam: tBNEG.negnam,
      negtax: tBNEG.negtax,
      neglmd: tBNEG.neglmd,
      neguid: tBNEG.neguid,
    });
  }

  protected createFromForm(): ITBNEG {
    return {
      ...new TBNEG(),
      negsts: this.editForm.get(['negsts'])!.value,
      negcod: this.editForm.get(['negcod'])!.value,
      negnam: this.editForm.get(['negnam'])!.value,
      negtax: this.editForm.get(['negtax'])!.value,
      neglmd: this.editForm.get(['neglmd'])!.value,
      neguid: this.editForm.get(['neguid'])!.value,
    };
  }
}
