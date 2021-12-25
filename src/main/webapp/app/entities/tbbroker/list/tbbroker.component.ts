import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBBROKER } from '../tbbroker.model';
import { TBBROKERService } from '../service/tbbroker.service';
import { TBBROKERDeleteDialogComponent } from '../delete/tbbroker-delete-dialog.component';

@Component({
  selector: 'jhi-tbbroker',
  templateUrl: './tbbroker.component.html',
})
export class TBBROKERComponent implements OnInit {
  tBBROKERS?: ITBBROKER[];
  isLoading = false;

  constructor(protected tBBROKERService: TBBROKERService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBBROKERService.query().subscribe(
      (res: HttpResponse<ITBBROKER[]>) => {
        this.isLoading = false;
        this.tBBROKERS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackBrcode(index: number, item: ITBBROKER): string {
    return item.brcode!;
  }

  delete(tBBROKER: ITBBROKER): void {
    const modalRef = this.modalService.open(TBBROKERDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBBROKER = tBBROKER;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
