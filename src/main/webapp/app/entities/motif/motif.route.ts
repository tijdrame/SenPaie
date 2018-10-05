import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MotifComponent } from './motif.component';
import { MotifDetailComponent } from './motif-detail.component';
import { MotifPopupComponent } from './motif-dialog.component';
import { MotifDeletePopupComponent } from './motif-delete-dialog.component';

@Injectable()
export class MotifResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const motifRoute: Routes = [
    {
        path: 'motif',
        component: MotifComponent,
        resolve: {
            'pagingParams': MotifResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.motif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'motif/:id',
        component: MotifDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.motif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const motifPopupRoute: Routes = [
    {
        path: 'motif-new',
        component: MotifPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.motif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'motif/:id/edit',
        component: MotifPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.motif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'motif/:id/delete',
        component: MotifDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.motif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
