import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBNEG } from '../tbneg.model';
import { TBNEGService } from '../service/tbneg.service';

@Component({
  templateUrl: './tbneg-delete-dialog.component.html',
})
export class TBNEGDeleteDialogComponent {
  tBNEG?: ITBNEG;

  constructor(protected tBNEGService: TBNEGService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBNEGService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
