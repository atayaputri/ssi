import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITBCOMS, TBCOMS } from '../tbcoms.model';
import { TBCOMSService } from '../service/tbcoms.service';
import { ITBJNSHM } from 'app/entities/tbjnshm/tbjnshm.model';
import { TBJNSHMService } from 'app/entities/tbjnshm/service/tbjnshm.service';

@Component({
  selector: 'jhi-tbcoms-update',
  templateUrl: './tbcoms-update.component.html',
})
export class TBCOMSUpdateComponent implements OnInit {
  isSaving = false;

  tBJNSHMSSharedCollection: ITBJNSHM[] = [];

  editForm = this.fb.group({
    costs: [null, [Validators.required]],
    cocode: [null, [Validators.required, Validators.maxLength(10)]],
    conam: [null, [Validators.required, Validators.maxLength(40)]],
    cocbei: [null, [Validators.required, Validators.maxLength(6)]],
    conbei: [null, [Validators.required, Validators.maxLength(40)]],
    cosat: [null, [Validators.required, Validators.maxLength(15)]],
    conom: [null, [Validators.required]],
    coisin: [null, [Validators.required, Validators.maxLength(15)]],
    conpwp: [null, [Validators.required, Validators.maxLength(20)]],
    coseri: [null, [Validators.required]],
    colshm: [null, [Validators.required]],
    colsks: [null, [Validators.required]],
    cotshm: [null, [Validators.required]],
    codshm: [null, [Validators.required]],
    conote1: [null, [Validators.maxLength(100)]],
    conote2: [null, [Validators.maxLength(100)]],
    conote3: [null, [Validators.maxLength(100)]],
    coskps: [null, [Validators.required]],
    cothld: [null, [Validators.required]],
    codir1: [null, [Validators.maxLength(30)]],
    codir2: [null, [Validators.maxLength(30)]],
    codir3: [null, [Validators.maxLength(30)]],
    codir4: [null, [Validators.maxLength(30)]],
    codir5: [null, [Validators.maxLength(30)]],
    colmd: [null, [Validators.required]],
    couid: [null, [Validators.required]],
    cojnsh: [],
  });

  constructor(
    protected tBCOMSService: TBCOMSService,
    protected tBJNSHMService: TBJNSHMService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBCOMS }) => {
      this.updateForm(tBCOMS);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBCOMS = this.createFromForm();
    if (tBCOMS.cocode !== undefined) {
      this.subscribeToSaveResponse(this.tBCOMSService.update(tBCOMS));
    } else {
      this.subscribeToSaveResponse(this.tBCOMSService.create(tBCOMS));
    }
  }

  trackTBJNSHMByJshcod(index: number, item: ITBJNSHM): string {
    return item.jshcod!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBCOMS>>): void {
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

  protected updateForm(tBCOMS: ITBCOMS): void {
    this.editForm.patchValue({
      costs: tBCOMS.costs,
      cocode: tBCOMS.cocode,
      conam: tBCOMS.conam,
      cocbei: tBCOMS.cocbei,
      conbei: tBCOMS.conbei,
      cosat: tBCOMS.cosat,
      conom: tBCOMS.conom,
      coisin: tBCOMS.coisin,
      conpwp: tBCOMS.conpwp,
      coseri: tBCOMS.coseri,
      colshm: tBCOMS.colshm,
      colsks: tBCOMS.colsks,
      cotshm: tBCOMS.cotshm,
      codshm: tBCOMS.codshm,
      conote1: tBCOMS.conote1,
      conote2: tBCOMS.conote2,
      conote3: tBCOMS.conote3,
      coskps: tBCOMS.coskps,
      cothld: tBCOMS.cothld,
      codir1: tBCOMS.codir1,
      codir2: tBCOMS.codir2,
      codir3: tBCOMS.codir3,
      codir4: tBCOMS.codir4,
      codir5: tBCOMS.codir5,
      colmd: tBCOMS.colmd,
      couid: tBCOMS.couid,
      cojnsh: tBCOMS.cojnsh,
    });

    this.tBJNSHMSSharedCollection = this.tBJNSHMService.addTBJNSHMToCollectionIfMissing(this.tBJNSHMSSharedCollection, tBCOMS.cojnsh);
  }

  protected loadRelationshipsOptions(): void {
    this.tBJNSHMService
      .query()
      .pipe(map((res: HttpResponse<ITBJNSHM[]>) => res.body ?? []))
      .pipe(
        map((tBJNSHMS: ITBJNSHM[]) => this.tBJNSHMService.addTBJNSHMToCollectionIfMissing(tBJNSHMS, this.editForm.get('cojnsh')!.value))
      )
      .subscribe((tBJNSHMS: ITBJNSHM[]) => (this.tBJNSHMSSharedCollection = tBJNSHMS));
  }

  protected createFromForm(): ITBCOMS {
    return {
      ...new TBCOMS(),
      costs: this.editForm.get(['costs'])!.value,
      cocode: this.editForm.get(['cocode'])!.value,
      conam: this.editForm.get(['conam'])!.value,
      cocbei: this.editForm.get(['cocbei'])!.value,
      conbei: this.editForm.get(['conbei'])!.value,
      cosat: this.editForm.get(['cosat'])!.value,
      conom: this.editForm.get(['conom'])!.value,
      coisin: this.editForm.get(['coisin'])!.value,
      conpwp: this.editForm.get(['conpwp'])!.value,
      coseri: this.editForm.get(['coseri'])!.value,
      colshm: this.editForm.get(['colshm'])!.value,
      colsks: this.editForm.get(['colsks'])!.value,
      cotshm: this.editForm.get(['cotshm'])!.value,
      codshm: this.editForm.get(['codshm'])!.value,
      conote1: this.editForm.get(['conote1'])!.value,
      conote2: this.editForm.get(['conote2'])!.value,
      conote3: this.editForm.get(['conote3'])!.value,
      coskps: this.editForm.get(['coskps'])!.value,
      cothld: this.editForm.get(['cothld'])!.value,
      codir1: this.editForm.get(['codir1'])!.value,
      codir2: this.editForm.get(['codir2'])!.value,
      codir3: this.editForm.get(['codir3'])!.value,
      codir4: this.editForm.get(['codir4'])!.value,
      codir5: this.editForm.get(['codir5'])!.value,
      colmd: this.editForm.get(['colmd'])!.value,
      couid: this.editForm.get(['couid'])!.value,
      cojnsh: this.editForm.get(['cojnsh'])!.value,
    };
  }
}
