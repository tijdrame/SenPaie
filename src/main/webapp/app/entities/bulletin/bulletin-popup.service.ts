import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';

@Injectable()
export class BulletinPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private bulletinService: BulletinService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.bulletinService.find(id)
                    .subscribe((bulletinResponse: HttpResponse<Bulletin>) => {
                        const bulletin: Bulletin = bulletinResponse.body;
                        if (bulletin.dateCreated) {
                            bulletin.dateCreated = {
                                year: bulletin.dateCreated.getFullYear(),
                                month: bulletin.dateCreated.getMonth() + 1,
                                day: bulletin.dateCreated.getDate()
                            };
                        }
                        if (bulletin.dateUpdated) {
                            bulletin.dateUpdated = {
                                year: bulletin.dateUpdated.getFullYear(),
                                month: bulletin.dateUpdated.getMonth() + 1,
                                day: bulletin.dateUpdated.getDate()
                            };
                        }
                        if (bulletin.dateDeleted) {
                            bulletin.dateDeleted = {
                                year: bulletin.dateDeleted.getFullYear(),
                                month: bulletin.dateDeleted.getMonth() + 1,
                                day: bulletin.dateDeleted.getDate()
                            };
                        }
                        this.ngbModalRef = this.bulletinModalRef(component, bulletin);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.bulletinModalRef(component, new Bulletin());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    bulletinModalRef(component: Component, bulletin: Bulletin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bulletin = bulletin;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
