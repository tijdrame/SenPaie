/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { AbsenceComponent } from '../../../../../../main/webapp/app/entities/absence/absence.component';
import { AbsenceService } from '../../../../../../main/webapp/app/entities/absence/absence.service';
import { Absence } from '../../../../../../main/webapp/app/entities/absence/absence.model';

describe('Component Tests', () => {

    describe('Absence Management Component', () => {
        let comp: AbsenceComponent;
        let fixture: ComponentFixture<AbsenceComponent>;
        let service: AbsenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AbsenceComponent],
                providers: [
                    AbsenceService
                ]
            })
            .overrideTemplate(AbsenceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbsenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbsenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Absence(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.absences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
