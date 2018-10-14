/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { TypeAbsenceComponent } from '../../../../../../main/webapp/app/entities/type-absence/type-absence.component';
import { TypeAbsenceService } from '../../../../../../main/webapp/app/entities/type-absence/type-absence.service';
import { TypeAbsence } from '../../../../../../main/webapp/app/entities/type-absence/type-absence.model';

describe('Component Tests', () => {

    describe('TypeAbsence Management Component', () => {
        let comp: TypeAbsenceComponent;
        let fixture: ComponentFixture<TypeAbsenceComponent>;
        let service: TypeAbsenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeAbsenceComponent],
                providers: [
                    TypeAbsenceService
                ]
            })
            .overrideTemplate(TypeAbsenceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeAbsenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeAbsenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeAbsence(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeAbsences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
