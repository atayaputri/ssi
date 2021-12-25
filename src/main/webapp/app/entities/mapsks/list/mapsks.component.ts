import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMAPSKS } from '../mapsks.model';
import { MAPSKSService } from '../service/mapsks.service';
import { MAPSKSDeleteDialogComponent } from '../delete/mapsks-delete-dialog.component';

@Component({
  selector: 'jhi-mapsks',
  templateUrl: './mapsks.component.html',
})
export class MAPSKSComponent implements OnInit {
  mAPSKS?: IMAPSKS[];
  isLoading = false;

  constructor(protected mAPSKSService: MAPSKSService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.mAPSKSService.query().subscribe(
      (res: HttpResponse<IMAPSKS[]>) => {
        this.isLoading = false;
        this.mAPSKS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMAPSKS): number {
    return item.id!;
  }

  delete(mAPSKS: IMAPSKS): void {
    const modalRef = this.modalService.open(MAPSKSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mAPSKS = mAPSKS;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
