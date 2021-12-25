import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBBROKER } from '../tbbroker.model';
import { TBBROKERService } from '../service/tbbroker.service';

@Component({
  templateUrl: './tbbroker-delete-dialog.component.html',
})
export class TBBROKERDeleteDialogComponent {
  tBBROKER?: ITBBROKER;

  constructor(protected tBBROKERService: TBBROKERService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBBROKERService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
