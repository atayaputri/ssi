import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LISTEMTDetailComponent } from './listemt-detail.component';

describe('LISTEMT Management Detail Component', () => {
  let comp: LISTEMTDetailComponent;
  let fixture: ComponentFixture<LISTEMTDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LISTEMTDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ lISTEMT: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LISTEMTDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LISTEMTDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load lISTEMT on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.lISTEMT).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
