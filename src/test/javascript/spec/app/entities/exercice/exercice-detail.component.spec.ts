/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { ExerciceDetailComponent } from '../../../../../../main/webapp/app/entities/exercice/exercice-detail.component';
import { ExerciceService } from '../../../../../../main/webapp/app/entities/exercice/exercice.service';
import { Exercice } from '../../../../../../main/webapp/app/entities/exercice/exercice.model';

describe('Component Tests', () => {

    describe('Exercice Management Detail Component', () => {
        let comp: ExerciceDetailComponent;
        let fixture: ComponentFixture<ExerciceDetailComponent>;
        let service: ExerciceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [ExerciceDetailComponent],
                providers: [
                    ExerciceService
                ]
            })
            .overrideTemplate(ExerciceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Exercice(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.exercice).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
