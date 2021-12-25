import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITBPROV, TBPROV } from '../tbprov.model';
import { TBPROVService } from '../service/tbprov.service';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';
import { TBNEGService } from 'app/entities/tbneg/service/tbneg.service';

@Component({
  selector: 'jhi-tbprov-update',
  templateUrl: './tbprov-update.component.html',
})
export class TBPROVUpdateComponent implements OnInit {
  isSaving = false;

  tBNEGSSharedCollection: ITBNEG[] = [];

  editForm = this.fb.group({
    provsts: [null, [Validators.required]],
    provcod: [null, [Validators.required, Validators.maxLength(4)]],
    provnam: [null, [Validators.required, Validators.maxLength(40)]],
    provlmd: [null, [Validators.required]],
    provuid: [null, [Validators.required]],
    provneg: [],
  });

  constructor(
    protected tBPROVService: TBPROVService,
    protected tBNEGService: TBNEGService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBPROV }) => {
      this.updateForm(tBPROV);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBPROV = this.createFromForm();
    if (tBPROV.provcod !== undefined) {
      this.subscribeToSaveResponse(this.tBPROVService.update(tBPROV));
    } else {
      this.subscribeToSaveResponse(this.tBPROVService.create(tBPROV));
    }
  }

  trackTBNEGByNegcod(index: number, item: ITBNEG): string {
    return item.negcod!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBPROV>>): void {
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

  protected updateForm(tBPROV: ITBPROV): void {
    this.editForm.patchValue({
      provsts: tBPROV.provsts,
      provcod: tBPROV.provcod,
      provnam: tBPROV.provnam,
      provlmd: tBPROV.provlmd,
      provuid: tBPROV.provuid,
      provneg: tBPROV.provneg,
    });

    this.tBNEGSSharedCollection = this.tBNEGService.addTBNEGToCollectionIfMissing(this.tBNEGSSharedCollection, tBPROV.provneg);
  }

  protected loadRelationshipsOptions(): void {
    this.tBNEGService
      .query()
      .pipe(map((res: HttpResponse<ITBNEG[]>) => res.body ?? []))
      .pipe(map((tBNEGS: ITBNEG[]) => this.tBNEGService.addTBNEGToCollectionIfMissing(tBNEGS, this.editForm.get('provneg')!.value)))
      .subscribe((tBNEGS: ITBNEG[]) => (this.tBNEGSSharedCollection = tBNEGS));
  }

  protected createFromForm(): ITBPROV {
    return {
      ...new TBPROV(),
      provsts: this.editForm.get(['provsts'])!.value,
      provcod: this.editForm.get(['provcod'])!.value,
      provnam: this.editForm.get(['provnam'])!.value,
      provlmd: this.editForm.get(['provlmd'])!.value,
      provuid: this.editForm.get(['provuid'])!.value,
      provneg: this.editForm.get(['provneg'])!.value,
    };
  }
}
