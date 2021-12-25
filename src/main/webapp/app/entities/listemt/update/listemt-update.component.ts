import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILISTEMT, LISTEMT } from '../listemt.model';
import { LISTEMTService } from '../service/listemt.service';

@Component({
  selector: 'jhi-listemt-update',
  templateUrl: './listemt-update.component.html',
})
export class LISTEMTUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    liscode: [null, [Validators.required, Validators.maxLength(4)]],
    lisnam: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
    lisdir: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30)]],
  });

  constructor(protected lISTEMTService: LISTEMTService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lISTEMT }) => {
      this.updateForm(lISTEMT);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lISTEMT = this.createFromForm();
    if (lISTEMT.id !== undefined) {
      this.subscribeToSaveResponse(this.lISTEMTService.update(lISTEMT));
    } else {
      this.subscribeToSaveResponse(this.lISTEMTService.create(lISTEMT));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILISTEMT>>): void {
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

  protected updateForm(lISTEMT: ILISTEMT): void {
    this.editForm.patchValue({
      id: lISTEMT.id,
      liscode: lISTEMT.liscode,
      lisnam: lISTEMT.lisnam,
      lisdir: lISTEMT.lisdir,
    });
  }

  protected createFromForm(): ILISTEMT {
    return {
      ...new LISTEMT(),
      id: this.editForm.get(['id'])!.value,
      liscode: this.editForm.get(['liscode'])!.value,
      lisnam: this.editForm.get(['lisnam'])!.value,
      lisdir: this.editForm.get(['lisdir'])!.value,
    };
  }
}
