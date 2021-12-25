import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILISTEMT } from '../listemt.model';
import { LISTEMTService } from '../service/listemt.service';
import { LISTEMTDeleteDialogComponent } from '../delete/listemt-delete-dialog.component';

@Component({
  selector: 'jhi-listemt',
  templateUrl: './listemt.component.html',
})
export class LISTEMTComponent implements OnInit {
  lISTEMTS?: ILISTEMT[];
  isLoading = false;

  constructor(protected lISTEMTService: LISTEMTService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.lISTEMTService.query().subscribe(
      (res: HttpResponse<ILISTEMT[]>) => {
        this.isLoading = false;
        this.lISTEMTS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILISTEMT): number {
    return item.id!;
  }

  delete(lISTEMT: ILISTEMT): void {
    const modalRef = this.modalService.open(LISTEMTDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lISTEMT = lISTEMT;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
