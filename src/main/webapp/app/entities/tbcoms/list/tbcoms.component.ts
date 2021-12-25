import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBCOMS } from '../tbcoms.model';
import { TBCOMSService } from '../service/tbcoms.service';
import { TBCOMSDeleteDialogComponent } from '../delete/tbcoms-delete-dialog.component';

@Component({
  selector: 'jhi-tbcoms',
  templateUrl: './tbcoms.component.html',
})
export class TBCOMSComponent implements OnInit {
  tBCOMS?: ITBCOMS[];
  isLoading = false;

  constructor(protected tBCOMSService: TBCOMSService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBCOMSService.query().subscribe(
      (res: HttpResponse<ITBCOMS[]>) => {
        this.isLoading = false;
        this.tBCOMS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackCocode(index: number, item: ITBCOMS): string {
    return item.cocode!;
  }

  delete(tBCOMS: ITBCOMS): void {
    const modalRef = this.modalService.open(TBCOMSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBCOMS = tBCOMS;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
