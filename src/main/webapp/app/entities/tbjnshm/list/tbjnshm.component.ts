import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBJNSHM } from '../tbjnshm.model';
import { TBJNSHMService } from '../service/tbjnshm.service';
import { TBJNSHMDeleteDialogComponent } from '../delete/tbjnshm-delete-dialog.component';

@Component({
  selector: 'jhi-tbjnshm',
  templateUrl: './tbjnshm.component.html',
})
export class TBJNSHMComponent implements OnInit {
  tBJNSHMS?: ITBJNSHM[];
  isLoading = false;

  constructor(protected tBJNSHMService: TBJNSHMService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBJNSHMService.query().subscribe(
      (res: HttpResponse<ITBJNSHM[]>) => {
        this.isLoading = false;
        this.tBJNSHMS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackJshcod(index: number, item: ITBJNSHM): string {
    return item.jshcod!;
  }

  delete(tBJNSHM: ITBJNSHM): void {
    const modalRef = this.modalService.open(TBJNSHMDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBJNSHM = tBJNSHM;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
