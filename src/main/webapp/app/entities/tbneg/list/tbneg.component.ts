import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBNEG } from '../tbneg.model';
import { TBNEGService } from '../service/tbneg.service';
import { TBNEGDeleteDialogComponent } from '../delete/tbneg-delete-dialog.component';

@Component({
  selector: 'jhi-tbneg',
  templateUrl: './tbneg.component.html',
})
export class TBNEGComponent implements OnInit {
  tBNEGS?: ITBNEG[];
  isLoading = false;

  constructor(protected tBNEGService: TBNEGService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBNEGService.query().subscribe(
      (res: HttpResponse<ITBNEG[]>) => {
        this.isLoading = false;
        this.tBNEGS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackNegcod(index: number, item: ITBNEG): string {
    return item.negcod!;
  }

  delete(tBNEG: ITBNEG): void {
    const modalRef = this.modalService.open(TBNEGDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBNEG = tBNEG;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
