import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LISTEMTService } from '../service/listemt.service';

import { LISTEMTComponent } from './listemt.component';

describe('LISTEMT Management Component', () => {
  let comp: LISTEMTComponent;
  let fixture: ComponentFixture<LISTEMTComponent>;
  let service: LISTEMTService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LISTEMTComponent],
    })
      .overrideTemplate(LISTEMTComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LISTEMTComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LISTEMTService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.lISTEMTS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
