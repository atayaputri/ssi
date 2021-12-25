jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TBTYPSService } from '../service/tbtyps.service';

import { TBTYPSDeleteDialogComponent } from './tbtyps-delete-dialog.component';

describe('TBTYPS Management Delete Component', () => {
  let comp: TBTYPSDeleteDialogComponent;
  let fixture: ComponentFixture<TBTYPSDeleteDialogComponent>;
  let service: TBTYPSService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBTYPSDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TBTYPSDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBTYPSDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBTYPSService);
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
