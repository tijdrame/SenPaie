/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { TypeRelationComponent } from '../../../../../../main/webapp/app/entities/type-relation/type-relation.component';
import { TypeRelationService } from '../../../../../../main/webapp/app/entities/type-relation/type-relation.service';
import { TypeRelation } from '../../../../../../main/webapp/app/entities/type-relation/type-relation.model';

describe('Component Tests', () => {

    describe('TypeRelation Management Component', () => {
        let comp: TypeRelationComponent;
        let fixture: ComponentFixture<TypeRelationComponent>;
        let service: TypeRelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeRelationComponent],
                providers: [
                    TypeRelationService
                ]
            })
            .overrideTemplate(TypeRelationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeRelationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeRelation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeRelations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
