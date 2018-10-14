/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { ExerciceComponent } from '../../../../../../main/webapp/app/entities/exercice/exercice.component';
import { ExerciceService } from '../../../../../../main/webapp/app/entities/exercice/exercice.service';
import { Exercice } from '../../../../../../main/webapp/app/entities/exercice/exercice.model';

describe('Component Tests', () => {

    describe('Exercice Management Component', () => {
        let comp: ExerciceComponent;
        let fixture: ComponentFixture<ExerciceComponent>;
        let service: ExerciceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [ExerciceComponent],
                providers: [
                    ExerciceService
                ]
            })
            .overrideTemplate(ExerciceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Exercice(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.exercices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
