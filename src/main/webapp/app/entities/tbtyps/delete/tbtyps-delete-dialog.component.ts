import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBTYPS } from '../tbtyps.model';
import { TBTYPSService } from '../service/tbtyps.service';

@Component({
  templateUrl: './tbtyps-delete-dialog.component.html',
})
export class TBTYPSDeleteDialogComponent {
  tBTYPS?: ITBTYPS;

  constructor(protected tBTYPSService: TBTYPSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBTYPSService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
