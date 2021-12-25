import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBTYPS } from '../tbtyps.model';
import { TBTYPSService } from '../service/tbtyps.service';
import { TBTYPSDeleteDialogComponent } from '../delete/tbtyps-delete-dialog.component';

@Component({
  selector: 'jhi-tbtyps',
  templateUrl: './tbtyps.component.html',
})
export class TBTYPSComponent implements OnInit {
  tBTYPS?: ITBTYPS[];
  isLoading = false;

  constructor(protected tBTYPSService: TBTYPSService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBTYPSService.query().subscribe(
      (res: HttpResponse<ITBTYPS[]>) => {
        this.isLoading = false;
        this.tBTYPS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackTpscod(index: number, item: ITBTYPS): string {
    return item.tpscod!;
  }

  delete(tBTYPS: ITBTYPS): void {
    const modalRef = this.modalService.open(TBTYPSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBTYPS = tBTYPS;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
