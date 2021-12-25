import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITABFEE, TABFEE } from '../tabfee.model';
import { TABFEEService } from '../service/tabfee.service';
import { ITABJTRX } from 'app/entities/tabjtrx/tabjtrx.model';
import { TABJTRXService } from 'app/entities/tabjtrx/service/tabjtrx.service';

@Component({
  selector: 'jhi-tabfee-update',
  templateUrl: './tabfee-update.component.html',
})
export class TABFEEUpdateComponent implements OnInit {
  isSaving = false;

  tABJTRXESSharedCollection: ITABJTRX[] = [];

  editForm = this.fb.group({
    id: [],
    fests: [null, [Validators.required]],
    feemt: [null, [Validators.required, Validators.maxLength(10)]],
    femin: [null, [Validators.required]],
    femax: [null, [Validators.required]],
    fefee: [null, [Validators.required]],
    fediscp: [null, [Validators.required]],
    fedisc: [null, [Validators.required]],
    fetax: [null, [Validators.required]],
    felmd: [null, [Validators.required]],
    feuid: [null, [Validators.required]],
    fejns: [],
  });

  constructor(
    protected tABFEEService: TABFEEService,
    protected tABJTRXService: TABJTRXService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tABFEE }) => {
      this.updateForm(tABFEE);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tABFEE = this.createFromForm();
    if (tABFEE.id !== undefined) {
      this.subscribeToSaveResponse(this.tABFEEService.update(tABFEE));
    } else {
      this.subscribeToSaveResponse(this.tABFEEService.create(tABFEE));
    }
  }

  trackTABJTRXByJtjntx(index: number, item: ITABJTRX): string {
    return item.jtjntx!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITABFEE>>): void {
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

  protected updateForm(tABFEE: ITABFEE): void {
    this.editForm.patchValue({
      id: tABFEE.id,
      fests: tABFEE.fests,
      feemt: tABFEE.feemt,
      femin: tABFEE.femin,
      femax: tABFEE.femax,
      fefee: tABFEE.fefee,
      fediscp: tABFEE.fediscp,
      fedisc: tABFEE.fedisc,
      fetax: tABFEE.fetax,
      felmd: tABFEE.felmd,
      feuid: tABFEE.feuid,
      fejns: tABFEE.fejns,
    });

    this.tABJTRXESSharedCollection = this.tABJTRXService.addTABJTRXToCollectionIfMissing(this.tABJTRXESSharedCollection, tABFEE.fejns);
  }

  protected loadRelationshipsOptions(): void {
    this.tABJTRXService
      .query()
      .pipe(map((res: HttpResponse<ITABJTRX[]>) => res.body ?? []))
      .pipe(
        map((tABJTRXES: ITABJTRX[]) => this.tABJTRXService.addTABJTRXToCollectionIfMissing(tABJTRXES, this.editForm.get('fejns')!.value))
      )
      .subscribe((tABJTRXES: ITABJTRX[]) => (this.tABJTRXESSharedCollection = tABJTRXES));
  }

  protected createFromForm(): ITABFEE {
    return {
      ...new TABFEE(),
      id: this.editForm.get(['id'])!.value,
      fests: this.editForm.get(['fests'])!.value,
      feemt: this.editForm.get(['feemt'])!.value,
      femin: this.editForm.get(['femin'])!.value,
      femax: this.editForm.get(['femax'])!.value,
      fefee: this.editForm.get(['fefee'])!.value,
      fediscp: this.editForm.get(['fediscp'])!.value,
      fedisc: this.editForm.get(['fedisc'])!.value,
      fetax: this.editForm.get(['fetax'])!.value,
      felmd: this.editForm.get(['felmd'])!.value,
      feuid: this.editForm.get(['feuid'])!.value,
      fejns: this.editForm.get(['fejns'])!.value,
    };
  }
}
