import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SexeComponent } from './sexe.component';
import { SexeDetailComponent } from './sexe-detail.component';
import { SexePopupComponent } from './sexe-dialog.component';
import { SexeDeletePopupComponent } from './sexe-delete-dialog.component';

@Injectable()
export class SexeResolvePagingParams implements Resolve<any> {

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

export const sexeRoute: Routes = [
    {
        path: 'sexe',
        component: SexeComponent,
        resolve: {
            'pagingParams': SexeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.sexe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sexe/:id',
        component: SexeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.sexe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sexePopupRoute: Routes = [
    {
        path: 'sexe-new',
        component: SexePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.sexe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sexe/:id/edit',
        component: SexePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.sexe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sexe/:id/delete',
        component: SexeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.sexe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
