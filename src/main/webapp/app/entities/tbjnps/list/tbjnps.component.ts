import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBJNPS } from '../tbjnps.model';
import { TBJNPSService } from '../service/tbjnps.service';
import { TBJNPSDeleteDialogComponent } from '../delete/tbjnps-delete-dialog.component';

@Component({
  selector: 'jhi-tbjnps',
  templateUrl: './tbjnps.component.html',
})
export class TBJNPSComponent implements OnInit {
  tBJNPS?: ITBJNPS[];
  isLoading = false;

  constructor(protected tBJNPSService: TBJNPSService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBJNPSService.query().subscribe(
      (res: HttpResponse<ITBJNPS[]>) => {
        this.isLoading = false;
        this.tBJNPS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackJpscod(index: number, item: ITBJNPS): string {
    return item.jpscod!;
  }

  delete(tBJNPS: ITBJNPS): void {
    const modalRef = this.modalService.open(TBJNPSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBJNPS = tBJNPS;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
