jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TBNEGService } from '../service/tbneg.service';

import { TBNEGDeleteDialogComponent } from './tbneg-delete-dialog.component';

describe('TBNEG Management Delete Component', () => {
  let comp: TBNEGDeleteDialogComponent;
  let fixture: ComponentFixture<TBNEGDeleteDialogComponent>;
  let service: TBNEGService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBNEGDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TBNEGDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBNEGDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBNEGService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
