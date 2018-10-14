/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { TypeRelationDetailComponent } from '../../../../../../main/webapp/app/entities/type-relation/type-relation-detail.component';
import { TypeRelationService } from '../../../../../../main/webapp/app/entities/type-relation/type-relation.service';
import { TypeRelation } from '../../../../../../main/webapp/app/entities/type-relation/type-relation.model';

describe('Component Tests', () => {

    describe('TypeRelation Management Detail Component', () => {
        let comp: TypeRelationDetailComponent;
        let fixture: ComponentFixture<TypeRelationDetailComponent>;
        let service: TypeRelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeRelationDetailComponent],
                providers: [
                    TypeRelationService
                ]
            })
            .overrideTemplate(TypeRelationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeRelationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeRelation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeRelation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
