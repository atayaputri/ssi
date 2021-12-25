import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBPART } from '../tbpart.model';
import { TBPARTService } from '../service/tbpart.service';
import { TBPARTDeleteDialogComponent } from '../delete/tbpart-delete-dialog.component';

@Component({
  selector: 'jhi-tbpart',
  templateUrl: './tbpart.component.html',
})
export class TBPARTComponent implements OnInit {
  tBPARTS?: ITBPART[];
  isLoading = false;

  constructor(protected tBPARTService: TBPARTService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBPARTService.query().subscribe(
      (res: HttpResponse<ITBPART[]>) => {
        this.isLoading = false;
        this.tBPARTS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackTpacode(index: number, item: ITBPART): string {
    return item.tpacode!;
  }

  delete(tBPART: ITBPART): void {
    const modalRef = this.modalService.open(TBPARTDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBPART = tBPART;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
