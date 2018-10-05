/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { AbsenceDetailComponent } from '../../../../../../main/webapp/app/entities/absence/absence-detail.component';
import { AbsenceService } from '../../../../../../main/webapp/app/entities/absence/absence.service';
import { Absence } from '../../../../../../main/webapp/app/entities/absence/absence.model';

describe('Component Tests', () => {

    describe('Absence Management Detail Component', () => {
        let comp: AbsenceDetailComponent;
        let fixture: ComponentFixture<AbsenceDetailComponent>;
        let service: AbsenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AbsenceDetailComponent],
                providers: [
                    AbsenceService
                ]
            })
            .overrideTemplate(AbsenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbsenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbsenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Absence(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.absence).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
