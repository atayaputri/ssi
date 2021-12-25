import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITBHDR, TBHDR } from '../tbhdr.model';
import { TBHDRService } from '../service/tbhdr.service';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';
import { TBJNPSService } from 'app/entities/tbjnps/service/tbjnps.service';

@Component({
  selector: 'jhi-tbhdr-update',
  templateUrl: './tbhdr-update.component.html',
})
export class TBHDRUpdateComponent implements OnInit {
  isSaving = false;

  tBJNPSSharedCollection: ITBJNPS[] = [];

  editForm = this.fb.group({
    thsts: [null, [Validators.required]],
    thno: [null, [Validators.required]],
    thsid: [null, [Validators.required, Validators.maxLength(20)]],
    thnm1: [null, [Validators.required, Validators.maxLength(50)]],
    thjsh: [null, [Validators.required]],
    thtax: [null, [Validators.required]],
    thdis: [null, [Validators.required]],
    thlmd: [null, [Validators.required]],
    thuid: [null, [Validators.required]],
    thfil1: [],
    thfil2: [],
    thfil3: [null, [Validators.maxLength(30)]],
    thfil4: [null, [Validators.maxLength(30)]],
    thjnps: [],
  });

  constructor(
    protected tBHDRService: TBHDRService,
    protected tBJNPSService: TBJNPSService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBHDR }) => {
      this.updateForm(tBHDR);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tBHDR = this.createFromForm();
    if (tBHDR.thno !== undefined) {
      this.subscribeToSaveResponse(this.tBHDRService.update(tBHDR));
    } else {
      this.subscribeToSaveResponse(this.tBHDRService.create(tBHDR));
    }
  }

  trackTBJNPSByJpscod(index: number, item: ITBJNPS): string {
    return item.jpscod!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITBHDR>>): void {
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

  protected updateForm(tBHDR: ITBHDR): void {
    this.editForm.patchValue({
      thsts: tBHDR.thsts,
      thno: tBHDR.thno,
      thsid: tBHDR.thsid,
      thnm1: tBHDR.thnm1,
      thjsh: tBHDR.thjsh,
      thtax: tBHDR.thtax,
      thdis: tBHDR.thdis,
      thlmd: tBHDR.thlmd,
      thuid: tBHDR.thuid,
      thfil1: tBHDR.thfil1,
      thfil2: tBHDR.thfil2,
      thfil3: tBHDR.thfil3,
      thfil4: tBHDR.thfil4,
      thjnps: tBHDR.thjnps,
    });

    this.tBJNPSSharedCollection = this.tBJNPSService.addTBJNPSToCollectionIfMissing(this.tBJNPSSharedCollection, tBHDR.thjnps);
  }

  protected loadRelationshipsOptions(): void {
    this.tBJNPSService
      .query()
      .pipe(map((res: HttpResponse<ITBJNPS[]>) => res.body ?? []))
      .pipe(map((tBJNPS: ITBJNPS[]) => this.tBJNPSService.addTBJNPSToCollectionIfMissing(tBJNPS, this.editForm.get('thjnps')!.value)))
      .subscribe((tBJNPS: ITBJNPS[]) => (this.tBJNPSSharedCollection = tBJNPS));
  }

  protected createFromForm(): ITBHDR {
    return {
      ...new TBHDR(),
      thsts: this.editForm.get(['thsts'])!.value,
      thno: this.editForm.get(['thno'])!.value,
      thsid: this.editForm.get(['thsid'])!.value,
      thnm1: this.editForm.get(['thnm1'])!.value,
      thjsh: this.editForm.get(['thjsh'])!.value,
      thtax: this.editForm.get(['thtax'])!.value,
      thdis: this.editForm.get(['thdis'])!.value,
      thlmd: this.editForm.get(['thlmd'])!.value,
      thuid: this.editForm.get(['thuid'])!.value,
      thfil1: this.editForm.get(['thfil1'])!.value,
      thfil2: this.editForm.get(['thfil2'])!.value,
      thfil3: this.editForm.get(['thfil3'])!.value,
      thfil4: this.editForm.get(['thfil4'])!.value,
      thjnps: this.editForm.get(['thjnps'])!.value,
    };
  }
}
