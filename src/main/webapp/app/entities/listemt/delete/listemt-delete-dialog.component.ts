import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILISTEMT } from '../listemt.model';
import { LISTEMTService } from '../service/listemt.service';

@Component({
  templateUrl: './listemt-delete-dialog.component.html',
})
export class LISTEMTDeleteDialogComponent {
  lISTEMT?: ILISTEMT;

  constructor(protected lISTEMTService: LISTEMTService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lISTEMTService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
