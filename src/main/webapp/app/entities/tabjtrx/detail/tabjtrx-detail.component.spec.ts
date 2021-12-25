import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TABJTRXDetailComponent } from './tabjtrx-detail.component';

describe('TABJTRX Management Detail Component', () => {
  let comp: TABJTRXDetailComponent;
  let fixture: ComponentFixture<TABJTRXDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TABJTRXDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tABJTRX: { jtjntx: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TABJTRXDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TABJTRXDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tABJTRX on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tABJTRX).toEqual(expect.objectContaining({ jtjntx: 'ABC' }));
    });
  });
});
