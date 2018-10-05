/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { CategorieDetailComponent } from '../../../../../../main/webapp/app/entities/categorie/categorie-detail.component';
import { CategorieService } from '../../../../../../main/webapp/app/entities/categorie/categorie.service';
import { Categorie } from '../../../../../../main/webapp/app/entities/categorie/categorie.model';

describe('Component Tests', () => {

    describe('Categorie Management Detail Component', () => {
        let comp: CategorieDetailComponent;
        let fixture: ComponentFixture<CategorieDetailComponent>;
        let service: CategorieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [CategorieDetailComponent],
                providers: [
                    CategorieService
                ]
            })
            .overrideTemplate(CategorieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Categorie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.categorie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
