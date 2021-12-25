import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITABJTRX } from '../tabjtrx.model';
import { TABJTRXService } from '../service/tabjtrx.service';
import { TABJTRXDeleteDialogComponent } from '../delete/tabjtrx-delete-dialog.component';

@Component({
  selector: 'jhi-tabjtrx',
  templateUrl: './tabjtrx.component.html',
})
export class TABJTRXComponent implements OnInit {
  tABJTRXES?: ITABJTRX[];
  isLoading = false;

  constructor(protected tABJTRXService: TABJTRXService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tABJTRXService.query().subscribe(
      (res: HttpResponse<ITABJTRX[]>) => {
        this.isLoading = false;
        this.tABJTRXES = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackJtjntx(index: number, item: ITABJTRX): string {
    return item.jtjntx!;
  }

  delete(tABJTRX: ITABJTRX): void {
    const modalRef = this.modalService.open(TABJTRXDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tABJTRX = tABJTRX;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
