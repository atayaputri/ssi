import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITABJTRX, TABJTRX } from '../tabjtrx.model';
import { TABJTRXService } from '../service/tabjtrx.service';

@Component({
  selector: 'jhi-tabjtrx-update',
  templateUrl: './tabjtrx-update.component.html',
})
export class TABJTRXUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    jtsts: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
    jtjntx: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1)]],
    jtdesc: [null, [Validators.minLength(1), Validators.maxLength(50)]],
    jtsdes: [null, [Validators.minLength(1), Validators.maxLength(20)]],
    jtlmd: [null, [Validators.required]],
    jtouid: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
  });

  constructor(protected tABJTRXService: TABJTRXService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tABJTRX }) => {
      this.updateForm(tABJTRX);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tABJTRX = this.createFromForm();
    if (tABJTRX.jtjntx !== undefined) {
      this.subscribeToSaveResponse(this.tABJTRXService.update(tABJTRX));
    } else {
      this.subscribeToSaveResponse(this.tABJTRXService.create(tABJTRX));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITABJTRX>>): void {
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

  protected updateForm(tABJTRX: ITABJTRX): void {
    this.editForm.patchValue({
      jtsts: tABJTRX.jtsts,
      jtjntx: tABJTRX.jtjntx,
      jtdesc: tABJTRX.jtdesc,
      jtsdes: tABJTRX.jtsdes,
      jtlmd: tABJTRX.jtlmd,
      jtouid: tABJTRX.jtouid,
    });
  }

  protected createFromForm(): ITABJTRX {
    return {
      ...new TABJTRX(),
      jtsts: this.editForm.get(['jtsts'])!.value,
      jtjntx: this.editForm.get(['jtjntx'])!.value,
      jtdesc: this.editForm.get(['jtdesc'])!.value,
      jtsdes: this.editForm.get(['jtsdes'])!.value,
      jtlmd: this.editForm.get(['jtlmd'])!.value,
      jtouid: this.editForm.get(['jtouid'])!.value,
    };
  }
}
