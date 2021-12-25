import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBCOMFO } from '../tbcomfo.model';
import { TBCOMFOService } from '../service/tbcomfo.service';
import { TBCOMFODeleteDialogComponent } from '../delete/tbcomfo-delete-dialog.component';

@Component({
  selector: 'jhi-tbcomfo',
  templateUrl: './tbcomfo.component.html',
})
export class TBCOMFOComponent implements OnInit {
  tBCOMFOS?: ITBCOMFO[];
  isLoading = false;

  constructor(protected tBCOMFOService: TBCOMFOService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBCOMFOService.query().subscribe(
      (res: HttpResponse<ITBCOMFO[]>) => {
        this.isLoading = false;
        this.tBCOMFOS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITBCOMFO): number {
    return item.id!;
  }

  delete(tBCOMFO: ITBCOMFO): void {
    const modalRef = this.modalService.open(TBCOMFODeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBCOMFO = tBCOMFO;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
