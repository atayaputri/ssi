import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITABJTRX } from '../tabjtrx.model';
import { TABJTRXService } from '../service/tabjtrx.service';

@Component({
  templateUrl: './tabjtrx-delete-dialog.component.html',
})
export class TABJTRXDeleteDialogComponent {
  tABJTRX?: ITABJTRX;

  constructor(protected tABJTRXService: TABJTRXService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tABJTRXService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
