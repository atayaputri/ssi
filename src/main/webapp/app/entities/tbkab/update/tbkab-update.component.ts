import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITBKAB, TBKAB } from '../tbkab.model';
import { TBKABService } from '../service/tbkab.service';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';
import { TBPROVService } from 'app/entities/tbprov/service/tbprov.service';

@Component({
  selector: 'jhi-tbkab-update',
  templateUrl: './tbkab-update.component.html',
})
export class TBKABUpdateComponent implements OnInit {
  isSaving = false;

  tBPROVSSharedCollection: ITBPROV[] = [];

  editForm = this.fb.group({
    kabsts: [null, [Validators.required]],
    kabcod: [null, [Validators.required, Validators.maxLength(4)]],
    kabnam: [null, [Validators.required, Validators.maxLength(40)]],
    kablmd: [null, [Validators.required]],
    kabuid: [null, [Validators.required]],
    kabprov: [],
  });

  constructor(
    protected tBKABService: TBKABService,
    protected tBPROVService: TBPROVService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBKAB }) => {
      this.updateForm(tBKAB);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBKAB = this.createFromForm();
    if (tBKAB.kabcod !== undefined) {
      this.subscribeToSaveResponse(this.tBKABService.update(tBKAB));
    } else {
      this.subscribeToSaveResponse(this.tBKABService.create(tBKAB));
    }
  }

  trackTBPROVByProvcod(index: number, item: ITBPROV): string {
    return item.provcod!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBKAB>>): void {
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

  protected updateForm(tBKAB: ITBKAB): void {
    this.editForm.patchValue({
      kabsts: tBKAB.kabsts,
      kabcod: tBKAB.kabcod,
      kabnam: tBKAB.kabnam,
      kablmd: tBKAB.kablmd,
      kabuid: tBKAB.kabuid,
      kabprov: tBKAB.kabprov,
    });

    this.tBPROVSSharedCollection = this.tBPROVService.addTBPROVToCollectionIfMissing(this.tBPROVSSharedCollection, tBKAB.kabprov);
  }

  protected loadRelationshipsOptions(): void {
    this.tBPROVService
      .query()
      .pipe(map((res: HttpResponse<ITBPROV[]>) => res.body ?? []))
      .pipe(map((tBPROVS: ITBPROV[]) => this.tBPROVService.addTBPROVToCollectionIfMissing(tBPROVS, this.editForm.get('kabprov')!.value)))
      .subscribe((tBPROVS: ITBPROV[]) => (this.tBPROVSSharedCollection = tBPROVS));
  }

  protected createFromForm(): ITBKAB {
    return {
      ...new TBKAB(),
      kabsts: this.editForm.get(['kabsts'])!.value,
      kabcod: this.editForm.get(['kabcod'])!.value,
      kabnam: this.editForm.get(['kabnam'])!.value,
      kablmd: this.editForm.get(['kablmd'])!.value,
      kabuid: this.editForm.get(['kabuid'])!.value,
      kabprov: this.editForm.get(['kabprov'])!.value,
    };
  }
}
