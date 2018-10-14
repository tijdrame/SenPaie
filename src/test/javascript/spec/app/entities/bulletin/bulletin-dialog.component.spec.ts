/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { BulletinDialogComponent } from '../../../../../../main/webapp/app/entities/bulletin/bulletin-dialog.component';
import { BulletinService } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.service';
import { Bulletin } from '../../../../../../main/webapp/app/entities/bulletin/bulletin.model';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { TypePaiementService } from '../../../../../../main/webapp/app/entities/type-paiement';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { RemboursementService } from '../../../../../../main/webapp/app/entities/remboursement';

describe('Component Tests', () => {

    describe('Bulletin Management Dialog Component', () => {
        let comp: BulletinDialogComponent;
        let fixture: ComponentFixture<BulletinDialogComponent>;
        let service: BulletinService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [BulletinDialogComponent],
                providers: [
                    CollaborateurService,
                    TypePaiementService,
                    UserService,
                    RemboursementService,
                    BulletinService
                ]
            })
            .overrideTemplate(BulletinDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BulletinDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulletinService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bulletin(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.bulletin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bulletinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bulletin();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.bulletin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bulletinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
